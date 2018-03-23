package com.systemspecs.payrollfileinterface;


import com.systemspecs.payrollfileinterface.model.FtpInfo;
import com.systemspecs.payrollfileinterface.utility.Utility;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.systemspecs.payrollfileinterface.utility.Utility.downloadFileFromFtp;
@SpringBootApplication
public class PayrollfileinterfaceApplication   implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(PayrollfileinterfaceApplication.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {
		List<FtpInfo> ftpInfos =jdbcTemplate.query(
				"select  server , port,passwords, users, remoteInDirectory,remoteProcessDirectory,  localDestinationDirectory,companyid, batchno,status from ftpdetails ",
				(rs, rowNum) -> new FtpInfo(
						rs.getString("server"), rs.getInt("port"), rs.getString("passwords"),
						rs.getString("users"),rs.getString("remoteInDirectory"),
						rs.getString("remoteProcessDirectory"),rs.getString("localDestinationDirectory"),rs.getString("companyid"),
						rs.getString("batchno"),rs.getString("status"))
		);


		log.info("Started process");
		log.info("ftpInfos >>>>>>>>>>"+ftpInfos.size());
		for (FtpInfo  ftpInfo : ftpInfos ){
			downloadFileFromFtp( ftpInfo.getUser(), ftpInfo.getPassword(), ftpInfo.getRemoteInDirectory(), ftpInfo.getRemoteProcessDirectory(),  ftpInfo.getLocalDestinationDirectory() , ftpInfo.getPort(), ftpInfo.getServer());

		}

	}

	public static void main(String[] args) {



		SpringApplication.run(PayrollfileinterfaceApplication.class, args);


	}



}
