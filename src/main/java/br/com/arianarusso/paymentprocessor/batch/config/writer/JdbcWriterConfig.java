package br.com.arianarusso.paymentprocessor.batch.config.writer;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class JdbcWriterConfig {
    @Bean
    @Primary
    public JdbcBatchItemWriter<Transaction> jdbcWriter(@Qualifier("appDataSource") DataSource dataSource){
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO transaction_batch (id, am" +
                "ount, timesstamp, receiver_id, sender_id) " +
                "VALUES (:id, :amount, :timesstamp, :receiver_id, :sender_id)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}
