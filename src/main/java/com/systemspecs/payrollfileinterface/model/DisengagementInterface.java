package com.systemspecs.payrollfileinterface.model;

public class DisengagementInterface {

    private String staffnumber;

    private String status;

    private String companyId;

    private String disengagmentDate;

    private String reason;

    private String comment;


    public String getStaffnumber() {
        return staffnumber;
    }

    public void setStaffnumber(String staffnumber) {
        this.staffnumber = staffnumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

}


