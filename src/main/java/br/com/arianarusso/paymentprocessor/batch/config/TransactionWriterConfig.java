package br.com.arianarusso.paymentprocessor.batch.config;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TransactionWriterConfig {

    private List<Transaction> processedTransactions = new ArrayList<>();

    public TransactionWriterConfig(List<Transaction> processedTransactions) {
        this.processedTransactions = processedTransactions;
    }

    @Bean

    public ItemWriter<Transaction> writerConsole() {
        return items -> {
            items.forEach(processedTransactions::add);
        };
    }

    public List<Transaction> getWrittenItems() {
        return processedTransactions;
    }

    @Bean
    @StepScope
    @Primary
    public ItemWriter<Transaction> jdbcWriter(@Qualifier("appDataSource") DataSource dataSource){
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO transaction_batch (id, am" +
                "ount, timesstamp, receiver_id, sender_id) " +
                "VALUES (:id, :amount, :timesstamp, :receiver_id, :sender_id)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}
