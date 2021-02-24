package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysSalary extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private long salaryId;
    private Integer salaryNumber;
    private String salaryName;
    private int salaryAge;
    private String salarySex;
    private double salaryPay;
    private String salaryStatus;
    private String salaryAddress;

    public long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(long salaryId) {
        this.salaryId = salaryId;
    }

    public Integer getSalaryNumber() {
        return salaryNumber;
    }

    public void setSalaryNumber(Integer salaryNumber) {
        this.salaryNumber = salaryNumber;
    }

    public String getSalaryName() {
        return salaryName;
    }

    public void setSalaryName(String salaryName) {
        this.salaryName = salaryName;
    }

    public int getSalaryAge() {
        return salaryAge;
    }

    public void setSalaryAge(int salaryAge) {
        this.salaryAge = salaryAge;
    }

    public String getSalarySex() {
        return salarySex;
    }

    public void setSalarySex(String salarySex) {
        this.salarySex = salarySex;
    }

    public double getSalaryPay() {
        return salaryPay;
    }

    public void setSalaryPay(double salaryPay) {
        this.salaryPay = salaryPay;
    }

    public String getSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(String salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    public String getSalaryAddress() {
        return salaryAddress;
    }

    public void setSalaryAddress(String salaryAddress) {
        this.salaryAddress = salaryAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("salaryId", getSalaryId())
                .append("salaryNumber", getSalaryNumber())
                .append("salaryName", getSalaryName())
                .append("salaryAge", getSalaryAge())
                .append("salarySex", getSalarySex())
                .append("salaryPay", getSalaryPay())
                .append("salaryStatus", getSalaryStatus())
                .append("salaryAddress", getSalaryAddress())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
