package br.com.arianarusso.paymentprocessor.batch.config;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionStepConfig {

    @Autowired
    private JobRepository jobRepository;

    //interface
    @Qualifier("transactionManagerApp")
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;


    @Bean
    public Step step (ItemWriter<Transaction> writer, ItemReader<Transaction> reader){
        return new StepBuilder("step", this.jobRepository)
                .<Transaction, Transaction>chunk(10, this.transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }



}
