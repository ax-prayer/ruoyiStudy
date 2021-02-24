<template>
    <div class="app-container">

    <!-- 查询框 
    inline行内元素，多个item共处一行 
    -->
    <el-form ref="searchForm" :inline="true" :model="searchMap" v-show="showSearch">
      <el-form-item prop="salaryName" >
          员工姓名
        <el-input 
          v-model="searchMap.salaryName"
          placeholder="请输入员工姓名" 
          size="small"
          style="width: 240px"
        ></el-input>
      </el-form-item>
      <el-form-item prop="salaryNumber">
          工号
        <el-input 
          v-model="searchMap.salaryNumber" 
          placeholder="请输入工号"
          oninput="value=value.replace(/[^0-9]/g,'')"
          size="small"
          style="width: 240px"
          ></el-input>
      </el-form-item>
      <el-form-item prop="salarySex">
          性别
        <el-select 
          v-model="searchMap.salarySex" 
          placeholder="选择员工性别"
        >
          <!-- 通过循环的形式展示出下拉菜单
          key必须添加，否则可能会出错，相当于唯一性标识
          -->
          <el-option v-for="sextype in sexType"
            :key="sextype.type"
            :label="sextype.name"
            :value="sextype.type">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <!-- el-button的type:影响颜色-->
          <el-button 
            type="primary"
            size="mini"
            icon="el-icon-search" 
            @click="fetchData"
          >搜索</el-button>
          <el-button
            size="mini"
            icon="el-icon-refresh" 
            @click="handleReset"
          >重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 按钮栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button 
          type="primary"
          plain
          size="mini"
          icon="el-icon-plus" 
          @click="handleDialog"
      >新增</el-button>
      <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single" 
          @click="handleEdit"
      >修改</el-button>
      <el-button
          type="danger"
          plain
          size="mini"
          icon="el-icon-delete"
          :disabled="multiple"
          @click="handleDelete"
      >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="fetchData"></right-toolbar>
    </el-row>

<!-- 表格
  ：data 绑定要渲染的数据
    border是否有纵向边框
    slot-scope="scope">
    通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据
    用|分割，前面获取传给后面，然后通过filters获取 
    <span>{{scope.row}}</span>
   v-loading="loading" 
  -->
    <div>
      <el-table
        v-loading="loading"
        :data="list"  
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <!-- type="index"获取list索引值作为序号 -->
        <el-table-column type="selection" width="55" align="center" ></el-table-column>
        <el-table-column type="index" label="序号" width="100" align="center"> </el-table-column>
        <el-table-column prop="salaryNumber" label="工号" align="center"> </el-table-column>
        <el-table-column prop="salaryName" label="姓名" align="center"> </el-table-column>
        <el-table-column prop="salarySex" label="性别" align="center">
          <template slot-scope="scope">
        <!-- 通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据
            用|分割，前面获取传给后面，然后通过filters获取 -->
            <span>{{scope.row.salarySex | sexTypeFilter}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="salaryAge" label="年龄" align="center"> </el-table-column>
        <el-table-column prop="salaryPay" label="工资" align="center"> </el-table-column>
        <el-table-column prop="salaryAddress" label="地址" align="center"> </el-table-column>
        <el-table-column prop="salaryStatus" label="状态" align="center">
          <template slot-scope="scope">
            <span>{{scope.row.salaryStatus | statusTypeFilter}}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建者" align="center" prop="createBy" width="100" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="100">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
            >编辑</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table> 
    </div>

    <!-- 分页
    handleSizeChange，更改每页个数 handleCurrentChange，更改每页内容
    将num,size放入searchmap传到后台
    -->
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchMap.pageNum"
      :page-sizes="[2, 5,10]"
      :page-size="searchMap.pageSize"
      :total="totalnum"
      class="paginationright"
      layout="total,sizes, prev, pager, next, jumper">
    </el-pagination>

    <!-- 弹出框
      title为弹框的标题
      :visible.sync为true的时候弹出框，所以“确定”按钮的点击事件设置为dialogFormVisible=true
      ref表单命名
      绑定属性pojo，要想校验不出错应该每个属性都写prop
      绑定rules设置校验规则，判断能不能为空
      autocomplete打开时是否填充上次的数据进去
    -->
    <el-dialog title="人员信息" :visible.sync="dialogFormVisible" width="600px">
      <el-form
        ref="pojoForm"
        :model="pojo" 
        label-width="80px" 
        label-position="right" 
        :rules="rules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="salaryName">
              <el-input v-model="pojo.salaryName" placeholder="请输入姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工号" prop="salaryNumber">
              <el-input
                v-model="pojo.salaryNumber" 
                placeholder="请输入工号"
                oninput="value=value.replace(/[^0-9]/g,'')"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="salarySex">
              <el-select v-model="pojo.salarySex" placeholder="请选择性别" style="width:150px">
                <el-option v-for="sextype in sexType"
                  :key="sextype.type"
                  :label="sextype.name"
                  :value="sextype.type">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="salaryAge">
              <el-input 
                v-model="pojo.salaryAge" 
                placeholder="请输入年龄"
                oninput="value=value.replace(/[^0-9]/g,'')"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>   
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="salaryStatus">
              <el-select v-model="pojo.salaryStatus" placeholder="请选择状态" style="width:150px">
                <el-option v-for="sttype in statusType"
                  :key="sttype.type"
                  :label="sttype.name"
                  :value="sttype.type">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工资" prop="salaryPay">
              <!--只能输入数字和小数点value=value.replace(/[^0-9.]/g,'')
                  只能输入数字value=value.replace(/[^0-9]/g,'')
                  只能输入两位小数value=value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')
                  同时限制数字和小数oninput="value=value.replace(/[^0-9.]/g,'').replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')"  
                -->
              <el-input 
              v-model="pojo.salaryPay" 
              placeholder="最多保留两位小数"
              oninput="value=value.replace(/[^0-9.]/g,'').replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3')" 
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="地址" prop="salaryAddress">
              <el-input 
                v-model="pojo.salaryAddress"
                placeholder="请输入地址" 
                type="textarea" maxlength="50"   
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!--根据是否有id来判断使用哪个函数，来实现一个弹出框的复用
            添加新成员没有id，修改信息会获取所在行的id
          -->
        <el-button type="primary" @click="pojo.salaryId===null?handleAdd('pojoForm'):handleUpdate('pojoForm')">确定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>

import {getList,addUser,getUserById,updateUser,deleteUser} from "@/api/system/salary";

// 过滤器的数据写在data外面，因为过滤器不能调用this.
const sexType = [
  {type:"0",name:'男'},
  {type:"1",name:'女'}
]
const statusType = [
  {type:"0",name:'在职'},
  {type:"1",name:'离职'}
]

export default {
  data(){
    return{
      // 遮罩层
      loading: true,
      //存放多选id，以数组形式请求
      ids:[],
      //非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //是否显示搜索条件
      showSearch: true,
      //表单渲染数据
      list: [],
      //性别状态类型写在外面，里面声明
      sexType,
      statusType,
      //分页默认总数据量
      totalnum:0,
      //多条件查询，默认分页的页码、每页个数
      searchMap:{
        salaryName:'',
        salaryNumber:null,
        salarySex:'',
        pageNum:1,
        pageSize:5
      },
      //弹出框的状态
      dialogFormVisible: false,
      // 弹出框表单信息。为了防止校验信息是否为空出错，给所有属性赋空值，为了弹出框的复用，让id默认为null
      pojo:{
        salaryId:null,
        salaryName:'',
        salaryNumber:'',
        salarySex:'',
        salaryAge:'',
        salaryPay:'',
        salaryAddress:'',
        salaryStatus:'',
      },
      //定义校验规则，trigger:'blur'表示当失去焦点的时候触发
      rules:{
        salaryName:[
          {required:true,message:'姓名不能为空',trigger:'blur'}
        ],
        salaryNumber:[
          {required:true,message:'工号不能为空',trigger:'blur'}
        ],
        salarySex:[
          {required:true,message:'性别不能为空',trigger:'change'}
        ]
      }
    }
  },

  // 钩子函数created()是在实例创建完成后被立即调用
  // 将获取所有人信息放入钩子函数中，每次创建实例后都自动调用
  created() {    
    this.fetchData()
  },

  methods: {
    fetchData(){
      this.loading = true;
      // 通过回调函数将获取的信息给list，进行表格的渲染
      getList(this.searchMap).then(response => {
        this.list = response.rows
        this.totalnum=response.total
        this.loading = false;
      })
    },

    //获取改变值重新加载页面
    handleSizeChange(val){
      this.searchMap.pageSize=val
      this.fetchData()
    },

    handleCurrentChange(val){
      this.searchMap.pageNum=val
      this.fetchData()
    },     

    // 编辑按钮打开方法，根据id获取用户信息，并放入pojo中对弹出框进行渲染
    // 在方法中调用handleDialog(),复用代码实现表单重置和弹出框
    handleEdit(row){
       this.handleDialog()
      const salaryId = row.salaryId || this.ids 
      getUserById(salaryId).then(response=>{
          this.pojo = response.data
      })
    },

    //修改按钮确定方法，更新用户信息
    //this.$refs[formName].validate(valid =>{if(valid){}
    //判断整个页面的校验信息是否符合    
    handleUpdate(formName){
      this.$refs[formName].validate(valid =>{
        if(valid){
          console.log(this.pojo.salaryId)
          updateUser(this.pojo).then(response =>{
            // 使用this.fetchData()进行刷新
            this.fetchData()
            this.dialogFormVisible = false
            this.pojo.salaryId=null
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
          })
        } else {
          return false
          }
      })
    },

    //多选框获取id值
    handleSelectionChange(val) {
      this.ids = val.map(item => item.salaryId)
      this.single = val.length!=1
      this.multiple = !val.length
    },

    //删除按钮
    handleDelete(row){
      const salaryIds = row.salaryId || this.ids 
      this.$confirm('此操作将永久删除所选用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          deleteUser(salaryIds).then(()=>{
            this.fetchData(),
            this.$message({
            type: 'success',
            message: '删除成功!'
            });
          })  
        })
    },

    //查询框重置
    handleReset(){
      this.$refs['searchForm'].resetFields()
    },

    // 弹出框的表单重置方法，每次打开清空
    //this.$nextTick是异步事件，弹出窗口后加载DOM需要时间，通过异步请求
    //等它加载完成再调用resetFields()
    //element自带表单重置方法resetFields()
    handleDialog(){
      this.dialogFormVisible = true
      this.$nextTick(()=>{
      this.$refs['pojoForm'].resetFields()
      })  
    },

    // 添加信息确定按钮
    // validate对整个表单进行校验
    handleAdd(formName) {
      this.$refs[formName].validate(valid =>{
        if(valid){
          addUser(this.pojo).then(response =>{
            this.fetchData(),
            this.dialogFormVisible = false
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
          })
        } else {
          return false
          }
      })
    }
  },

  //过滤器，数字类型和汉字的转换
  //两种写法都可以，type是传入的数字，与过滤器数据对比
  // sexType.find(obj=>{
  // return obj.type === type})
  filters:{
    sexTypeFilter(type){
      const sexTy = sexType.find(obj => obj.type === type)
      return sexTy ? sexTy.name : null
    },
    statusTypeFilter(type){
      const statusTy = statusType.find(obj => obj.type === type)
      return statusTy ? statusTy.name : null
    },
  }
}
</script>


<style>

/* 分页组件靠右 */
.paginationright{
  float:right;
  margin-top:30px;
}

</style>