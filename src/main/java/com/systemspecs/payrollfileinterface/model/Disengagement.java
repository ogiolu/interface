package com.systemspecs.payrollfileinterface.model;

import java.util.Date;

public class Disengagement {


    private String disengagmentDate;

    private String reason;

    private String commment;

    private String staffRecord;

    private String nextApprover;

    private String status;

    private String companyId;

    private String enterby;

    public String getDisengagmentDate() {
        return disengagmentDate;
    }

    public void setDisengagmentDate(String disengagmentDate) {
        this.disengagmentDate = disengagmentDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCommment() {
        return commment;
    }

    public void setCommment(String commment) {
        this.commment = commment;
    }

    public String getStaffRecord() {
        return staffRecord;
    }

    public void setStaffRecord(String staffRecord) {
        this.staffRecord = staffRecord;
    }

    public String getNextApprover() {
        return nextApprover;
    }

    public void setNextApprover(String nextApprover) {
        this.nextApprover = nextApprover;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }
}


