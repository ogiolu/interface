package com.systemspecs.payrollfileinterface.config;
import com.systemspecs.payrollfileinterface.itemProcessor.*;
import com.systemspecs.payrollfileinterface.model.*;
import com.systemspecs.payrollfileinterface.notificationListener.CompletionNotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    public static final String USERS_TEMP_DOCUMENTS_UPLOADFILE = "/Users/TEMP/Documents/uploadfile/in/";
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    private  Resource[] resources;

    private Integer batchno;
    @Value("${companyid}")
    private String  companyid;

    @Value("${inwardlocation}") // value after ':' is the default
    private String  inwardlocation;

    @Value("${batchno}")
    private String  batchnumber;






    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);


    @Bean
    public FlatFileItemReader<Promotioninterface> reader() {
        FlatFileItemReader<Promotioninterface> reader = new FlatFileItemReader<Promotioninterface>();
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"staffnumber","newMonthlyBasic","newGrossAmount","amountType","oldPaygroupCode","newPaygroupCode","effectiveDate"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Promotioninterface>() {{
                setTargetType(Promotioninterface.class);
            }});
        }});
        return reader;
    }

    ///@Primary
    @Bean
    public MultiResourceItemReader<Promotioninterface> multiResourceItemReader() {

        MultiResourceItemReader<Promotioninterface> resourceItemReader = new MultiResourceItemReader<Promotioninterface>();
        Resource[] resources = getResources("salrev_*.csv");
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @Bean
    public MultiResourceItemReader<DisengagementInterface> disengagementMultiResourceItemReader() {

        MultiResourceItemReader<DisengagementInterface> resourceItemReader = new MultiResourceItemReader<DisengagementInterface>();
        Resource[] resources = getResources("exits_*.csv");
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(disengangereader());
        return resourceItemReader;
    }


    private  Resource[] getResources(String fileName){
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = new Resource[0];
        System.out.println(" >>>>>>>.getResources "+inwardlocation +fileName );
        try {

            resources = resolver.getResources("file:" + inwardlocation +fileName );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    @Bean
    public MultiResourceItemReader<TransferInterface> transferMultiResourceItemReader() {

        MultiResourceItemReader<TransferInterface> resourceItemReader = new MultiResourceItemReader<TransferInterface>();
        Resource[] resources = getResources("transfer_*.csv");
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(transferreader());
        return resourceItemReader;
    }

    @Bean
    public MultiResourceItemReader<NewStaffInterface> newstaffMultiResourceItemReader() {

        MultiResourceItemReader<NewStaffInterface> resourceItemReader = new MultiResourceItemReader<NewStaffInterface>();
        Resource[] resources = getResources("newstaff_*.csv");
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(newstaffreader());
        return resourceItemReader;
    }

    @Bean
    public MultiResourceItemReader<UpdateStaffInterface> updatestaffMultiResourceItemReader() {

        MultiResourceItemReader<UpdateStaffInterface> resourceItemReader = new MultiResourceItemReader<UpdateStaffInterface>();
        Resource[] resources = getResources("updates_*.csv");
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(updatestaffreader());
        return resourceItemReader;
    }



    @Bean
    public FlatFileItemReader<DisengagementInterface> disengangereader() {
        FlatFileItemReader<DisengagementInterface> reader = new FlatFileItemReader<DisengagementInterface>();
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"staffnumber","disengagmentDate","reason","comment"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<DisengagementInterface>() {{
                setTargetType(DisengagementInterface.class);
            }});
        }});
        return reader;
    }

    @Bean
    public FlatFileItemReader<TransferInterface> transferreader() {
        FlatFileItemReader<TransferInterface> reader = new FlatFileItemReader<TransferInterface>();
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"staffnumber","effectivedate","analysis1","analysis2","analysis3","analysis4","analysis5","analysis6"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TransferInterface>() {{
                setTargetType(TransferInterface.class);
            }});
        }});
        return reader;
    }


    @Bean
    public FlatFileItemReader<NewStaffInterface> newstaffreader() {
        FlatFileItemReader<NewStaffInterface> reader = new FlatFileItemReader<NewStaffInterface>();
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"staffnumber","corpid","surname","othername","title","sex","maritalstatus","phonenumber","email","jobtitle","taxoffice","rsapin","monthlysalary","employmentdate","confirmationdate","accountno","taxamount","taxno","noofchild","noofdep","effectivesalarydate","bankid","pfaid","paygroupno","nationality","state","lga","employmenttype","dob","religion","accounttype","maidensurname","origintown","contactaddress","nokname","nokphone","relationship","nokemail","nokaddress","eduinst1","eduinsttype1","eduqualification1","edugrade1","eduyear1","educourse1","eduinst2","eduinsttype2","eduqualification2","edugrade2","eduyear2","educourse2","eduinst3","eduinsttype3","eduqualification3","edugrade3","eduyear3","educourse3","eduinst4","eduinsttype4","eduqualification4","edugrade4","eduyear4","educourse4","eduinst5","eduinsttype5","eduqualification5","edugrade5","eduyear5","educourse5","bvn","analysis1","analysis2","analysis3","analysis4","analysis5","analysis6"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<NewStaffInterface>() {{
                setTargetType(NewStaffInterface.class);
            }});
        }});
        return reader;
    }


    @Bean
    public FlatFileItemReader<UpdateStaffInterface> updatestaffreader() {
        FlatFileItemReader<UpdateStaffInterface> reader = new FlatFileItemReader<UpdateStaffInterface>();
        reader.setLineMapper(new DefaultLineMapper() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"staffnumber","corpid","surname","othername","title","sex","maritalstatus","phonenumber","email","jobtitle","taxoffice","rsapin","employmentdate","confirmationdate","accountno","taxamount","taxno","noofchild","noofdep","bankid","pfaid","nationality","state","lga","employmenttype","dob","religion","accounttype","maidensurname","origintown","contactaddress","nokname","nokphone","relationship","nokemail","nokaddress","eduinst1","eduinsttype1","eduqualification1","edugrade1","eduyear1","educourse1","eduinst2","eduinsttype2","eduqualification2","edugrade2","eduyear2","educourse2","eduinst3","eduinsttype3","eduqualification3","edugrade3","eduyear3","educourse3","eduinst4","eduinsttype4","eduqualification4","edugrade4","eduyear4","educourse4","eduinst5","eduinsttype5","eduqualification5","edugrade5","eduyear5","educourse5","bvn"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<UpdateStaffInterface>() {{
                setTargetType(UpdateStaffInterface.class);
            }});
        }});
        return reader;
    }


    @Bean
    public PromotionItemProcessor processor() {
        return new PromotionItemProcessor();
    }

    @Bean
    public DisengagementItemProcessor disengageprocessor() {
        return new DisengagementItemProcessor();
    }

    @Bean
    public TransferItemProcessor transferItemprocessor() {
        return new TransferItemProcessor();
    }

    @Bean
    public NewStaffItemProcessor newstaffItemprocessor() {
        return new NewStaffItemProcessor();
    }
    @Bean
    public UpdateStaffItemProcessor updatestaffItemprocessor() {
        return new UpdateStaffItemProcessor();
    }
    @Bean
    public JdbcBatchItemWriter<Promotioninterface> writer() {
        JdbcBatchItemWriter<Promotioninterface> writer = new JdbcBatchItemWriter<Promotioninterface>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Promotioninterface>());
        writer.setSql("insert into promotioninterface(staffnumber,newMonthlyBasic,newGrossAmount,amountType,oldPaygroupCode,newPaygroupCode,effectiveDate,companyId,status) values(:staffnumber,:newMonthlyBasic,:newGrossAmount,:amountType,:oldPaygroupCode,:newPaygroupCode,:effectiveDate,'"+companyid+"','A')");
         writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public JdbcBatchItemWriter<DisengagementInterface> disengagementwriter() {


        JdbcBatchItemWriter<DisengagementInterface> writer = new JdbcBatchItemWriter<DisengagementInterface>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<DisengagementInterface>());
        writer.setSql("insert into DISENGAGEMENTINTERFACE(staffnumber,disengagmentDate,reason,companyId,status,batchno) values(:staffnumber,:disengagmentDate,:reason,'"+companyid+"','A','1')");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<TransferInterface> transferwriter() {
        JdbcBatchItemWriter<TransferInterface> writer = new JdbcBatchItemWriter<TransferInterface>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TransferInterface>());
        writer.setSql("insert into TRANSFERINTERFACE(staffnumber,effectivedate,companyId,status,analysis1,analysis2,analysis3,analysis4,analysis5,analysis6) values(:staffnumber,:effectivedate,'"+companyid+"','A',:analysis1,:analysis2,:analysis3,:analysis4,:analysis5,:analysis6)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<NewStaffInterface> newstaffwriter() {
        JdbcBatchItemWriter<NewStaffInterface> writer = new JdbcBatchItemWriter<NewStaffInterface>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<NewStaffInterface>());
        String sql ="insert into newstaffinterface (staffnumber,corpid,surname,othername,title,sex,maritalstatus,phonenumber,email,jobtitle,taxoffice,rsapin,monthlysalary,employmentdate,confirmationdate,accountno,taxamount ,taxno,noofchild,noofdep,effectivesalarydate,bankid,pfaid,paygroupno,nationality,state,lga,employmenttype,dob,religion,accounttype,maidensurname,origintown,contactaddress,nokname,nokphone,relationship,nokemail,nokaddress,eduinst1,eduinsttype1,eduqualification1,edugrade1,eduyear1,educourse1,eduinst2,eduinsttype2,eduqualification2,edugrade2,eduyear2,educourse2,eduinst3,eduinsttype3,eduqualification3,edugrade3,eduyear3,educourse3,eduinst4,eduinsttype4,eduqualification4,edugrade4,eduyear4,educourse4,eduinst5,eduinsttype5,eduqualification5,edugrade5,eduyear5,educourse5,bvn,analysis1,analysis2,analysis3,analysis4,analysis5,analysis6,status,companyid) values(:staffnumber,:corpid,:surname,:othername,:title,:sex,:maritalstatus,:phonenumber,:email,:jobtitle,:taxoffice,:rsapin,:monthlysalary,:employmentdate,:confirmationdate,:accountno,:taxamount ,:taxno,:noofchild,:noofdep,:effectivesalarydate,:bankid,:pfaid,:paygroupno,:nationality,:state,:lga,:employmenttype,:dob,:religion,:accounttype,:maidensurname,:origintown,:contactaddress,:nokname,:nokphone,:relationship,:nokemail,:nokaddress,:eduinst1,:eduinsttype1,:eduqualification1,:edugrade1,:eduyear1,:educourse1,:eduinst2,:eduinsttype2,:eduqualification2,:edugrade2,:eduyear2,:educourse2,:eduinst3,:eduinsttype3,:eduqualification3,:edugrade3,:eduyear3,:educourse3,:eduinst4,:eduinsttype4,:eduqualification4,:edugrade4,:eduyear4,:educourse4,:eduinst5,:eduinsttype5,:eduqualification5,:edugrade5,:eduyear5,:educourse5,:bvn,:analysis1,:analysis2,:analysis3,:analysis4,:analysis5,:analysis6,'A','"+companyid+"')";
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public JdbcBatchItemWriter<UpdateStaffInterface> updatestaffwriter() {
        JdbcBatchItemWriter<UpdateStaffInterface> writer = new JdbcBatchItemWriter<UpdateStaffInterface>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<UpdateStaffInterface>());
        String sql ="insert into newstaffinterface (staffnumber,corpid,surname,othername,title,sex,maritalstatus,phonenumber,email,jobtitle,taxoffice,rsapin,employmentdate,confirmationdate,accountno,taxamount ,taxno,noofchild,noofdep,bankid,pfaid,nationality,state,lga,employmenttype,dob,religion,accounttype,maidensurname,origintown,contactaddress,nokname,nokphone,relationship,nokemail,nokaddress,eduinst1,eduinsttype1,eduqualification1,edugrade1,eduyear1,educourse1,eduinst2,eduinsttype2,eduqualification2,edugrade2,eduyear2,educourse2,eduinst3,eduinsttype3,eduqualification3,edugrade3,eduyear3,educourse3,eduinst4,eduinsttype4,eduqualification4,edugrade4,eduyear4,educourse4,eduinst5,eduinsttype5,eduqualification5,edugrade5,eduyear5,educourse5,bvn,status,companyid) values(:staffnumber,:corpid,:surname,:othername,:title,:sex,:maritalstatus,:phonenumber,:email,:jobtitle,:taxoffice,:rsapin,:employmentdate,:confirmationdate,:accountno,:taxamount ,:taxno,:noofchild,:noofdep,:bankid,:pfaid,:nationality,:state,:lga,:employmenttype,:dob,:religion,:accounttype,:maidensurname,:origintown,:contactaddress,:nokname,:nokphone,:relationship,:nokemail,:nokaddress,:eduinst1,:eduinsttype1,:eduqualification1,:edugrade1,:eduyear1,:educourse1,:eduinst2,:eduinsttype2,:eduqualification2,:edugrade2,:eduyear2,:educourse2,:eduinst3,:eduinsttype3,:eduqualification3,:edugrade3,:eduyear3,:educourse3,:eduinst4,:eduinsttype4,:eduqualification4,:edugrade4,:eduyear4,:educourse4,:eduinst5,:eduinsttype5,:eduqualification5,:edugrade5,:eduyear5,:educourse5,'U','"+companyid+"',)";
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(CompletionNotificationListener listener) {
        return jobBuilderFactory.get("importPayrollJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .next(step5())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Promotioninterface, Promotioninterface> chunk(10)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer())
                .build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<DisengagementInterface, DisengagementInterface> chunk(10)
                .reader(disengagementMultiResourceItemReader())
                .processor(disengageprocessor() )
                .writer( disengagementwriter())
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .<TransferInterface, TransferInterface> chunk(10)
                .reader(transferMultiResourceItemReader())
                .processor(transferItemprocessor() )
                .writer( transferwriter())
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .<NewStaffInterface, NewStaffInterface> chunk(10)
                .reader(newstaffMultiResourceItemReader())
                .processor(newstaffItemprocessor() )
                .writer(newstaffwriter())
                .build();
    }


    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .<UpdateStaffInterface, UpdateStaffInterface> chunk(10)
                .reader(updatestaffMultiResourceItemReader())
                .processor(updatestaffItemprocessor() )
                .writer(updatestaffwriter())
                .build();
    }
    // end::jobstep[]
}