package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.domain.SysSalary;
import com.ruoyi.system.service.ISysSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/system/salary")
public class SysSalaryController extends BaseController {

    @Resource
    private ISysSalaryService salaryService;

    //获取列表信息
    @GetMapping("/list")
    public TableDataInfo list(SysSalary salary)
    {
        startPage();
        List<SysSalary> list = salaryService.findAllByCondition(salary);
        return getDataTable(list);
    }

    //根据id获取信息
    @GetMapping(value ="/{salaryId}")
    public AjaxResult getInfo(@PathVariable long salaryId)
    {
        System.out.println(salaryId);
        return AjaxResult.success(salaryService.findSalaryById(salaryId));
    }

    //添加用户信息
    @Log(title = "工资管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysSalary salary)
    {
        salary.setCreateBy(SecurityUtils.getUsername());
        salary.setCreateTime(new Date());
        return toAjax(salaryService.addSalary(salary));
    }

    //修改用户信息
    @Log(title = "工资管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysSalary salary)
    {
        salary.setUpdateBy(SecurityUtils.getUsername());
        salary.setUpdateTime(new Date());
        return toAjax(salaryService.modifySalary(salary));
    }

    //根据id删除信息
    @Log(title = "工资管理", businessType = BusinessType.DELETE)
    @DeleteMapping(value ="/{salaryIds}")
    public AjaxResult remove(@PathVariable long[] salaryIds)
    {
        return toAjax(salaryService.removedSalaryByIds(salaryIds));
    }

}
