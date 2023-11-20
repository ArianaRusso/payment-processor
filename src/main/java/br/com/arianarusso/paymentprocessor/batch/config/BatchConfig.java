package br.com.arianarusso.paymentprocessor.batch.config;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.processing.Processor;
import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

//@Configuration
//public class BatchConfig {
//
////    @Autowired
////    private JobBuilder jobBuilder;
////
////    @Autowired
////    private StepBuilder stepBuilder;
//
//    @Autowired
//    private JobRepository jobRepository;
//
//    @Autowired
//    private PlatformTransactionManager transactionManager;
//
////    @Bean
////    @ConfigurationProperties(prefix = "spring.datasource")
////    public DataSource dataSource() {
////        return DataSourceBuilder.create().build();
////    }
//
////    @Bean
////    public DataSource dataSource() {
////        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
////        dataSource.setDriver(new org.postgresql.Driver());
////        dataSource.setUrl("jdbc:postgresql://localhost:5432/banking-system");
////        dataSource.setUsername("postgres");
////        dataSource.setPassword("root");
////        return dataSource;
////    }
//
////    @Bean
////    public PlatformTransactionManager transactionManager() {
////        return new DataSourceTransactionManager(dataSource());
////    }
//
//    @Bean
//    public Job job(Step step) {
//        return new JobBuilder("job", this.jobRepository)
//                .start(step)
//                .incrementer(new RunIdIncrementer())
//                .listener(
//                        JobListenerFactoryBean.getListener(new JobLoggerListener())
//                )
//                .build();
//    }
//
//    @Bean
//    public Step imprimiOlhaMundo() {
//        return new StepBuilder("step", this.jobRepository)
//                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
//                    System.out.println("Olá mundo");
//                    return RepeatStatus.FINISHED;
//                }, this.transactionManager)
//                .build();
//    }
//
//
//
//}
