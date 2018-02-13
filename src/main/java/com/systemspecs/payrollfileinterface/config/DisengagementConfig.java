package com.systemspecs.payrollfileinterface.config;

import com.systemspecs.payrollfileinterface.model.Disengagement;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class DisengagementConfig {
/*
    MultiResourceItemReader<Disengagement>   readFiles(@Value("${input}") Resource resource)   {
        return  new MultiResourceItemReader<Disengagement> ();
    }
*/
    @Bean
    FlatFileItemReader<Disengagement> fileReader(@Value("${input}") Resource resource) throws Exception {

        return new FlatFileItemReaderBuilder<Disengagement>()

                .resource(resource)
                .name("disengegment-reader")
                .targetType(Disengagement.class)
                .delimited().delimiter(",").names(new String[]{"disengagmentDate","reason","staffRecord","companyId"})
                .build();
    }

    @Bean
    JdbcBatchItemWriter<Disengagement> jdbcWriter(DataSource ds){
        return  new JdbcBatchItemWriterBuilder<Disengagement>()
                .dataSource(ds)
                .sql("insert into disengagement(disengagmentDate,reason,staffRecord,companyId) values(:disengagmentDate,:reason,:staffRecord,:companyId)")
                .beanMapped()
                .build();
    }
    @Bean
    Job job(JobBuilderFactory jbf, StepBuilderFactory sbf, ItemReader<? extends Disengagement> ir, ItemWriter<? super Disengagement> iw){
        Step s2= sbf.get("file-to-db-disengage").<Disengagement,Disengagement>chunk(100).reader(ir).writer(iw).build();
        return jbf.get("etl-prom").incrementer(new RunIdIncrementer()).start(s2).build();

    }
/*
    @Bean
    Job job(JobBuilderFactory jbf, StepBuilderFactory sbf, ItemReader<? extends Disengagement> ir, ItemWriter<? super Disengagement> iw){
        Step s1= sbf.get("file-to-db").<Disengagement,Disengagement>chunk(100).reader(ir).writer(iw).build();
        return jbf.get("etl").incrementer(new RunIdIncrementer()).start(s1).build();

    }
    */

}
