package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.domain.SysSalary;

import java.util.List;
import java.util.Map;

public interface ISysSalaryService {
    int addSalary(SysSalary salary);

    int removedSalaryByIds(long[] salaryIds);

    int modifySalary(SysSalary salary);

//    List<SysSalary> findUserById(Map<String, Object> queryMap);

    SysSalary findSalaryById(long salaryId);

    //List<SysSalary> findAll();

    //List<SysSalary> findAllByCondition(Map<String, Object> queryMap);

    List<SysSalary> findAllByCondition(SysSalary salary);




}
