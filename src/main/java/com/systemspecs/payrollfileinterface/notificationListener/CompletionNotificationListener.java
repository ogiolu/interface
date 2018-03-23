package com.systemspecs.payrollfileinterface.notificationListener;

import com.opencsv.CSVWriter;
import com.systemspecs.payrollfileinterface.model.FtpInfo;
import com.systemspecs.payrollfileinterface.model.Promotioninterface;
import com.systemspecs.payrollfileinterface.utility.Utility;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(CompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    Utility utility;

    @Autowired
    public CompletionNotificationListener(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Utility utility = new Utility();

            log.info("!!! JOB FINISHED! Time to verify the results");
            String localDestinationErrorDirectory = "C:/Users/TEMP/Documents/uploadfile/error";
            String csvFilename = localDestinationErrorDirectory+"/error.csv";

            log.info("About to move Processed file");
            //moveProcessedfile(localDestinationDirectory,localDestinationProcessedDirectory);
            log.info("Done to move Processed file");
            List<Promotioninterface> results = jdbcTemplate.query("SELECT STAFFNUMBER, companyId FROM Promotioninterface", new RowMapper<Promotioninterface>() {
                @Override
                public Promotioninterface mapRow(ResultSet rs, int row) throws SQLException {
                    return new Promotioninterface();
                }
            });

                List<FtpInfo> ftpInfos =jdbcTemplate.query(
                        "select  server , port,passwords, users, remoteInDirectory,remoteProcessDirectory,  localDestinationDirectory,companyid, batchno,status from ftpdetails ",
                        (rs, rowNum) -> new FtpInfo(
                                rs.getString("server"), rs.getInt("port"), rs.getString("passwords"),
                                rs.getString("users"),rs.getString("remoteInDirectory"),
                                rs.getString("remoteProcessDirectory"),rs.getString("localDestinationDirectory"),rs.getString("companyid"),
                                rs.getString("batchno"),rs.getString("status"))
                );

            try {
                log.info("Moving error file");
                //boolean putfile= utility.putFileInFtpLocation(ftpInfos);
                //log.info("putfile >>>>>>>>>>>>"+putfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            for (Promotioninterface promotioninterface : results) {
                log.info("Found <" + promotioninterface + "> in the database.");
            }

        }
    }



    public void moveProcessedfile (String sourceFolder,String destinationFolder){

        File srcDir = new File(sourceFolder);
        File destDir = new File(destinationFolder);

            try {
                FileUtils.copyDirectory(srcDir, destDir);
                FileUtils.cleanDirectory(srcDir);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }


}
