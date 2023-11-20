package br.com.arianarusso.paymentprocessor.batch.config;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class TransactionChunkConfig {

    @Bean
    public ItemReader<Transaction> reader(){
        return new FlatFileItemReaderBuilder<Transaction>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("transactions.txt"))
                .lineTokenizer(transactionLineTokenizer())
                .fieldSetMapper(transactionFieldSetMapper())
                .build();

    }

    private LineTokenizer transactionLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames("id", "amount", "timestamp", "receiver_id", "sender_id");
        return tokenizer;
    }

    private FieldSetMapper<Transaction> transactionFieldSetMapper() {
        return fieldSet -> {
            Transaction transaction = new Transaction();
            transaction.setId(UUID.fromString(fieldSet.readString("id")));
            transaction.setAmount(new BigDecimal(fieldSet.readString("amount")));
            transaction.setTimesstamp(LocalDateTime.parse(fieldSet.readString("timestamp")));
            transaction.setReceiver_id(UUID.fromString(fieldSet.readString("receiver_id")));
            transaction.setSender_id(UUID.fromString(fieldSet.readString("sender_id")));
            return transaction;
        };
    }

    @Bean
    @StepScope
    public ItemWriter<Transaction> writer(@Qualifier("appDataSource") DataSource dataSource){
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO transaction_batch (id, am" +
                "ount, timesstamp, receiver_id, sender_id) " +
                "VALUES (:id, :amount, :timesstamp, :receiver_id, :sender_id)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}
