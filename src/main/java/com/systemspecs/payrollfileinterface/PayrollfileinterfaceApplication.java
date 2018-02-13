package com.systemspecs.payrollfileinterface;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.*;

import static com.systemspecs.payrollfileinterface.utility.Utility.downloadFileFromFtp;

@EnableBatchProcessing
@SpringBootApplication
public class PayrollfileinterfaceApplication {

	public static void main(String[] args) {


      if(downloadFileFromFtp()){

		  System.setProperty("output","file:" +new File("/Users/TEMP/Documents/uploadfile/out.csv").getAbsolutePath());
		  System.setProperty("input","file:" +new File("/Users/TEMP/Documents/uploadfile/in.csv").getAbsolutePath());
		  System.setProperty("prominput","file:" +new File("/Users/TEMP/Documents/uploadfile/prom.csv").getAbsolutePath());
		  System.setProperty("prominputs","file:" +new File("/Users/TEMP/Documents/uploadfile/prom_*.csv").getAbsolutePath());
		  SpringApplication.run(PayrollfileinterfaceApplication.class, args);
	  }



	}


}
