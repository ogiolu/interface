package com.systemspecs.payrollfileinterface.model;

import java.io.Serializable;
import java.util.Date;

public class Promotioninterface implements Serializable {

    /**

     */
    private static final long serialVersionUID = 1L;

    private Long id;

    public String getStaffnumber() {
        return staffnumber;
    }

    public void setStaffnumber(String staffnumber) {
        this.staffnumber = staffnumber;
    }

    private String staffnumber;

    private String status = "P";

    private String companyId;

    private String comments;

    private String gradeLevel;

    private String amountType;

    private String newPaygroupCode;

    private String oldPaygroupCode;

    private Double newMonthlyBasic = 0d;

    private Double newGrossAmount = 0d;

    private String  effectiveDate;

    public String getOldPaygroupCode() {
        return oldPaygroupCode;
    }

    public void setOldPaygroupCode(String oldPaygroupCode) {
        this.oldPaygroupCode = oldPaygroupCode;
    }

    private Integer batchNo;




    public String getGradeLevel() {
        return gradeLevel;
    }


    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }


    public String getEffectiveDate() {
        return effectiveDate;
    }


    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }



    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }



    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getNewPaygroupCode() {
        return newPaygroupCode;
    }


    public void setNewPaygroupCode(String newPaygroupCode) {
        this.newPaygroupCode = newPaygroupCode;
    }


    public Double getNewMonthlyBasic() {
        return newMonthlyBasic;
    }


    public void setNewMonthlyBasic(Double newMonthlyBasic) {
        this.newMonthlyBasic = newMonthlyBasic;
    }


    public Double getNewGrossAmount() {
        return newGrossAmount;
    }


    public void setNewGrossAmount(Double newGrossAmount) {
        this.newGrossAmount = newGrossAmount;
    }


    public String getCompanyId() {
        return companyId;
    }


    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public Integer getBatchNo() {
        return batchNo;
    }


    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }


    public String getAmountType() {
        return amountType;
    }


    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }
}
