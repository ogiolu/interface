package com.systemspecs.payrollfileinterface.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FtpInfo {
   private  String server ="192.9.201.81";
   private int port = 21;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteInDirectory() {
        return remoteInDirectory;
    }

    public void setRemoteInDirectory(String remoteInDirectory) {
        this.remoteInDirectory = remoteInDirectory;
    }

    public String getRemoteProcessDirectory() {
        return remoteProcessDirectory;
    }

    public void setRemoteProcessDirectory(String remoteProcessDirectory) {
        this.remoteProcessDirectory = remoteProcessDirectory;
    }

    public String getLocalDestinationDirectory() {
        return localDestinationDirectory;
    }

    public void setLocalDestinationDirectory(String localDestinationDirectory) {
        this.localDestinationDirectory = localDestinationDirectory;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private  String user = "ftpuser";
   private String password = "development1.";
   private String remoteInDirectory = "/in/";
   private String remoteProcessDirectory = "/processed/";
   private String localDestinationDirectory = "C:/Users/TEMP/Documents/uploadfile/";
   private String companyid = "TRANSTECH";
   private String batchno="1";
   private String status="N";


    public FtpInfo(String server ,int port,String password, String user, String remoteInDirectory,
                                String remoteProcessDirectory, String localDestinationDirectory, String companyid, String batchno, String status){
        this.batchno=batchno;
        this.companyid=companyid;
        this.localDestinationDirectory=localDestinationDirectory;
        this.password=password;
        this.port=port;
        this.remoteInDirectory=remoteInDirectory;
        this.server=server;
        this.user=user;
        this.remoteProcessDirectory=remoteProcessDirectory;
        this.status=status;
    }

}
