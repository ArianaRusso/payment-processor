package br.com.arianarusso.paymentprocessor.batch.config.writer;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompositeWriteConfig {
    @Bean
    public CompositeItemWriter<Transaction> compositeTransactionWriter(
            FlatFileItemWriter<Transaction> flatFileItemWriter,
            JdbcBatchItemWriter<Transaction> jdbcBatchItemWriter){
        return new CompositeItemWriterBuilder<Transaction>()
                .delegates(flatFileItemWriter, jdbcBatchItemWriter)
                .build();
    }
}
