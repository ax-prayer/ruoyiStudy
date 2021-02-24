package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysSalary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysSalaryMapper {

    int insertSalary(SysSalary salary);

    int deleteSalaryByIds(long[] salaryId);


    int updateSalary(SysSalary salary);

    SysSalary selectSalaryById(long salaryId);

    //List<SysSalary> selectAllSalary();

    List<SysSalary> selectAllSalaryByCondition(SysSalary salary);
}
