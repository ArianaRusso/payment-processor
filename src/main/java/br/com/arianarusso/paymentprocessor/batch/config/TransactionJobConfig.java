package br.com.arianarusso.paymentprocessor.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionJobConfig {

    @Bean
    public Job job(@Qualifier("stepSalved") Step step, JobRepository jobRepository, JobTransactionListener listener) {
        return new JobBuilder("transaction-job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .build();
    }
}
