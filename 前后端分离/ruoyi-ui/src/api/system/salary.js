import request from '@/utils/request'

//查询列表信息
export function getList(searchMap){
            return request({
                url:'/system/salary/list',
                method:'get',
                params:searchMap
                })
			}
//添加人员信息
export function addUser(pojo){
            return request({
                url:'/system/salary',
                method:'post',
                data:pojo
				})
			}
//获取用户信息
export function getUserById(salaryId){
            return request({
                url:'/system/salary/' + salaryId,
                method:'get'
				})
			}
//修改用户信息
export function updateUser(pojo) {
            return request({
                url:'/system/salary',
                method:'put',
                data:pojo
				})
			}
//删除用户信息
export function deleteUser(salaryId){
            return request({
                url:'/system/salary/' + salaryId,
                method:'delete',
				})
			}

////////////////////////////////////////////////////////////////////
//统一export，在vue中使用api.方法名调用//import salaryapi from '@/api/system/salary'
// export default{
//     // 获取列表信息
//     getuser(searchMap){
//         return request({
//             url:'/system/salary/conditionuser',
//             method:'get',
//             params:searchMap
//         })
//     },
//     //添加新用户
//     adduser(pojo){
//         return request({
//             url:'/system/salary',
//             method:'post',
//             data:pojo
//         })
//     },
//     //获取用户信息
//     getuserById(salaryId){
//         return request({
//             url:'/system/salary/' + salaryId,
//             method:'get'
//         })
//     },
//     //修改用户信息
//     updateuser(pojo) {
//         return request({
//             url:'/system/salary',
//             method:'put',
//             data:pojo
//         })
//     },
//     // 删除信息
//     deleteUsers(salaryIds){
//         return request({
//             url:'/system/salary/' + salaryIds,
//             method:'delete',
//         })
//     }
// }