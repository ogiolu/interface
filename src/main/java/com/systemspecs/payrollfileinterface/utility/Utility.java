package com.systemspecs.payrollfileinterface.utility;

import com.systemspecs.payrollfileinterface.PayrollfileinterfaceApplication;
import com.systemspecs.payrollfileinterface.config.AppConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public class Utility {
    @Autowired
    private static AppConfig appConfig;

    public static Boolean downloadFileFromFtp(){
        final Logger log= LoggerFactory.getLogger(PayrollfileinterfaceApplication.class);
       String server ="192.9.201.81";
        int port = 21;
        String user = "ftpuser";
        String pass = "development1.";
        String remotedirectory = "/in/";
        String destinationdirectory = "C:/Users/TEMP/Documents/uploadfile/";


        FTPClient ftpClient = new FTPClient();
        FileUtils fileUtils =new FileUtils();
        try {
            setFtpInfo(server, port, user, pass, ftpClient);
           FTPFile[] ftpclients= ftpClient.listFiles(remotedirectory);
            for (FTPFile ftpclient : ftpclients) {
               String remoteFile =remotedirectory+ftpclient.getName();
                OutputStream outputStream =new BufferedOutputStream(new FileOutputStream(destinationdirectory+ftpclient.getName()));
               ftpClient.retrieveFile(remoteFile,outputStream);
                outputStream.close();
            }
            ftpClient.logout();
            ftpClient.disconnect();
            return true;

        } catch (IOException e) {
            log.error("Error Occurred while retrieving file "+e.getMessage());
            e.printStackTrace();
            return false;
        }


    }

    private static void setFtpInfo(String server, int port, String user, String pass, FTPClient ftpClient) throws IOException {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
}
