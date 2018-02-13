package com.systemspecs.payrollfileinterface.config;

import com.systemspecs.payrollfileinterface.model.Promotion;
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
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;


@Configuration
public class PromotionConfig {
    @Value("${prominputs}")
    private  Resource[] resources;
/*
      public   MultiResourceItemReader<Promotion> readFiles(@Value("${input}") Resource[] resource) throws Exception {


            //  new MultiResourceItemReader
            return  new MultiResourceItemReader<Promotion> ()
                    .
                    .setResources(resource);
        }
     */
   @Bean
    public MultiResourceItemReader<Promotion> multiResourceItem(@Value("${prominput}") Resource resource) throws Exception {
        MultiResourceItemReader<Promotion> multiResourceItemReader=
                new MultiResourceItemReader<Promotion>();
        multiResourceItemReader.setResources(resources);
        multiResourceItemReader.setDelegate(fileReader(resource));
        return  multiResourceItemReader;
    }

    @Bean
    @Primary
    FlatFileItemReader<Promotion> fileReader(@Value("${prominput}") Resource resource) throws Exception {

        return new FlatFileItemReaderBuilder<Promotion>()
                .resource(resource)
                .name("promotion-reader")
                .targetType(Promotion.class)
                .delimited().delimiter(",").names(new String[]{"staffnumber","nextApprover"})
                .build();
    }

    @Bean
    JdbcBatchItemWriter<Promotion> jdbcWriter(DataSource ds){
        return  new JdbcBatchItemWriterBuilder<Promotion>()
                .dataSource(ds)
                .sql("insert into promotion(staffnumber,nextApprover) values(:staffnumber,:nextApprover)")
                .beanMapped()
                .build();
    }

    @Bean
    Job job(JobBuilderFactory jbf, StepBuilderFactory sbf, ItemReader<? extends Promotion> promir, ItemWriter<? super Promotion> promiw){
        Step s2= sbf.get("file-to-db-prom")
                .<Promotion,Promotion>chunk(100)
                .reader(promir)
                .writer(promiw)
                .build();
        return jbf.get("etl-prom").incrementer(new RunIdIncrementer()).start(s2).build();

    }

}
