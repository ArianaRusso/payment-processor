package br.com.arianarusso.paymentprocessor.batch.config;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class TransactionReaderConfig {

    @Bean
    public ItemReader<Transaction> readerFile(){
        return new FlatFileItemReaderBuilder<Transaction>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("transactions.txt"))
                .delimited()
                .names(new String []{"id", "amount", "timestamp", "receiver_id", "sender_id"})
                .fieldSetMapper(transactionFieldSetMapper())
                .build();

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
    public JdbcCursorItemReader<Transaction> jdbcCursorReader(
            @Qualifier("appDataSource")DataSource dataSource){
        return new JdbcCursorItemReaderBuilder<Transaction>()
                .name("jdbcCursorReader")
                .dataSource(dataSource)
                .sql("select * from transaction_batch")
                .rowMapper(new BeanPropertyRowMapper<Transaction>(Transaction.class))
                .build();

    }

    @Bean
    public JdbcPagingItemReader<Transaction> jdbcPagingReader(
            @Qualifier("appDataSource")DataSource dataSource, PagingQueryProvider queryProvider){
        return new JdbcPagingItemReaderBuilder<Transaction>()
                .name("jdbcPagingReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .pageSize(2)
                .rowMapper(new BeanPropertyRowMapper<Transaction>(Transaction.class))
                .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("appDataSource")DataSource dataSource){
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from transaction_batch");
        queryProvider.setSortKey("amount");
        return queryProvider;
    }

}
