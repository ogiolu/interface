package com.systemspecs.payrollfileinterface.utility;

import com.jcraft.jsch.*;
import com.opencsv.CSVWriter;
import com.systemspecs.payrollfileinterface.config.AppConfig;
import com.systemspecs.payrollfileinterface.model.FtpInfo;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.tools.FileObject;
import java.io.*;
import java.util.List;
import java.util.Vector;

@Component
public class Utility {
    @Autowired
    private static AppConfig appConfig;

    @Autowired
    JdbcTemplate jdbcTemplate;




    public  Boolean putFileInFtpLocation(  List<FtpInfo> ftpInfos  ) throws Exception {
         final Logger log= LoggerFactory.getLogger(Utility.class);


        FTPClient ftpClient = new FTPClient();
        try {
            String localDestinationErrorDirectory = "C:/Users/TEMP/Documents/uploadfile/error/error.csv";
            String csvFilename = localDestinationErrorDirectory+"/error.csv";
            String errorFilename="error.csv";
            for (FtpInfo  ftpInfo : ftpInfos ){
               // setFtpInfo(ftpInfo.getServer(), ftpInfo.getPort(), ftpInfo.getUser(), ftpInfo.getPassword(), ftpClient);
               // uploadFTPFile(localDestinationErrorDirectory,csvFilename,ftpInfo.getRemoteInDirectory(),ftpClient);
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

    public void writeCSVFile(){
        String csvFilename="";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFilename));
            String[] header="STAFFNUMBER,TRANSACTIONTYPE,ERRORMESSAGE".split(",");
            writer.writeNext(header);
            // writer.wr
            //writer.writeAll(,true);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public  void uploadFTPFile(String localFileFullName, String fileName, String hostDir, FTPClient ftpClient)
            throws Exception
    {
        try {
            InputStream input = new FileInputStream(new File(localFileFullName));
            boolean storefile =ftpClient.storeFile(hostDir + fileName, input);
        }
        catch(Exception e){
            System.out.println("error in store error file"+e.getMessage());
            e.printStackTrace();
        }
    }


    public static Boolean downloadFileFromFtp(String user,String password,String remoteInDirectory,String remoteProcessDirectory, String localDestinationDirectory ,int port,String server){
        final Logger log= LoggerFactory.getLogger(Utility.class);

       FTPClient ftpClient = new FTPClient();
       //FTPSClient ftpClient = new FTPSClient();
       // SftpClient

        try {
            //setFtpInfo(server, port, user, password, ftpClient);

            log.info("remoteInDirectory >>>>>"+remoteInDirectory);
            JSch jsch = new JSch();
            Session session = null;
            String SFTPWORKINGDIR = "/files/In";

            try {
                session = jsch.getSession("INT017", "sftp.biscuits.com", 22);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword("dh}hitca");
                session.connect();
                log.info("connection >>>>>");
                Channel channel = session.openChannel("sftp");
                channel.connect();
                ChannelSftp sftpChannel = (ChannelSftp) channel;
                sftpChannel.cd(SFTPWORKINGDIR);

                Vector<ChannelSftp.LsEntry> list = sftpChannel.ls("*.csv");
                log.info("list >>>>>"+list.size());
                for(ChannelSftp.LsEntry entry : list) {

                    byte[] buffer = new byte[1024];
                    BufferedInputStream bis = new BufferedInputStream(sftpChannel.get(entry.getFilename()));
                    File newFile = new File("C:/Users/TEMP/Documents/uploadfile/in/"+entry.getFilename());
                    OutputStream os = new FileOutputStream(newFile);
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    int readCount;
                    while( (readCount = bis.read(buffer)) > 0) {
                        System.out.println("Writing: "+entry.getFilename() );
                        bos.write(buffer, 0, readCount);
                    }
                    bis.close();
                    bos.close();
                    System.out.println(entry.getFilename());
                }

                //sftpChannel.get("remotefile.txt", "localfile.txt");
                sftpChannel.exit();
                session.disconnect();
            } catch (JSchException e) {
                e.printStackTrace();
            } catch (SftpException e) {
                e.printStackTrace();
            }

            /*
            FTPFile[] ftpfiles= ftpClient.listFiles(remoteInDirectory);
            for (FTPFile ftpfile : ftpfiles) {
                String remoteFile =remoteInDirectory+ftpfile.getName();
                log.info("remoteFile >>>>>"+remoteFile);
                OutputStream outputStream =new BufferedOutputStream(new FileOutputStream(localDestinationDirectory+ftpfile.getName()));
                boolean downloaded  =ftpClient.retrieveFile(remoteFile,outputStream);

                log.info("downloaded >>>>>"+downloaded+">>>>>localDestinationDirectory>>" +localDestinationDirectory);
                ftpClient.rename(remoteInDirectory+ftpfile.getName(), remoteProcessDirectory+ftpfile.getName());
                log.info("Moved Prcoess File >>>>>");
                //ftpClient.deleteFile(remoteInDirectory+ftpfile.getName());
                outputStream.close();
            }
            ftpClient.logout();
            ftpClient.disconnect();
            */
            return true;

        } catch (Exception e) {
            log.error("Error Occurred while retrieving file "+e.getMessage());
            e.printStackTrace();
            return false;
        }


    }






  public  List<FtpInfo> loadFTPDetails(){
    List<FtpInfo> ftpInfos =jdbcTemplate.query(
            "select  server , port,passwords, users, remoteInDirectory,remoteProcessDirectory,  localDestinationDirectory,companyid, batchno,status from ftpdetails ",
            (rs, rowNum) -> new FtpInfo(
                    rs.getString("server"), rs.getInt("port"), rs.getString("passwords"),
                    rs.getString("users"),rs.getString("remoteInDirectory"),
                    rs.getString("remoteProcessDirectory"),rs.getString("localDestinationDirectory"),rs.getString("companyid"),
                    rs.getString("batchno"),rs.getString("status"))
    );

    return ftpInfos;

  }


    private static void setFtpInfo(String server, int port, String user, String pass, FTPClient ftpClient) throws IOException {
      System.out.print("server >>"+server+">>>>port >>>"+port+">>>user >>>>>"+user+">>>>>>>>pass >>>>>"+pass);
        ftpClient.connect(server, port);
        boolean login  =ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
}
