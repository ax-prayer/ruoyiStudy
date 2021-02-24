package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysSalary;
import com.ruoyi.system.mapper.SysSalaryMapper;
import com.ruoyi.system.service.ISysSalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysSalaryServiceImpl implements ISysSalaryService {

    private static final Logger log = LoggerFactory.getLogger(SysSalaryServiceImpl.class);

    @Resource
    private SysSalaryMapper salaryMapper;

    @Override
    public int addSalary(SysSalary salary) {
        return salaryMapper.insertSalary(salary);
    }

    @Override
    public int removedSalaryByIds(long[] salaryId) {
        return salaryMapper.deleteSalaryByIds(salaryId);
    }

    @Override
    public int modifySalary(SysSalary salary) {
        return salaryMapper.updateSalary(salary);
    }

    @Override
    public SysSalary findSalaryById(long salaryId) {
        return salaryMapper.selectSalaryById(salaryId);
    }

    //@Override
//    public List<SysSalary> findAll() {
//        return salaryMapper.selectAllSalary();
//    }

    @Override
    public List<SysSalary> findAllByCondition(SysSalary salary ) {
        return salaryMapper.selectAllSalaryByCondition(salary);
    }
}
