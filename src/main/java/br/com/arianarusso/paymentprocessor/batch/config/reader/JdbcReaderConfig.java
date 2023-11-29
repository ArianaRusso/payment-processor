package br.com.arianarusso.paymentprocessor.batch.config.reader;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcReaderConfig {

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
