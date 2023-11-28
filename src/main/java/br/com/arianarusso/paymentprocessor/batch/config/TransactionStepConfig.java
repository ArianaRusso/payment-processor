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

@Configuration
public class TransactionStepConfig {

    @Autowired
    private JobRepository jobRepository;

    //interface
    @Qualifier("transactionManagerApp")
    @Autowired
    private PlatformTransactionManager transactionManager;


    @Bean
    public Step stepSalve (@Qualifier("jdbcWriter") ItemWriter<Transaction> writer,  @Qualifier ("readerFile") ItemReader<Transaction> readerTransaction){
        return new StepBuilder("step", this.jobRepository)
                .<Transaction, Transaction>chunk(2, this.transactionManager)
                .reader(readerTransaction)
                .writer(writer)
                .build();
    }
    @Bean
    public Step stepSalved (@Qualifier("writerConsole") ItemWriter<Transaction> writer, @Qualifier("jdbcCursorItemReader") ItemReader<Transaction> readerTransaction){
        return new StepBuilder("step", this.jobRepository)
                .<Transaction, Transaction>chunk(2, this.transactionManager)
                .reader(readerTransaction)
                .writer(writer)
                .build();
    }
}
