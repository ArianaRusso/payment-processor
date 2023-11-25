package br.com.arianarusso.paymentprocessor.batch.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcItemWriter {
    //    @Bean
//    @StepScope
//    public ItemWriter<Transaction> jdbcWriter(@Qualifier("appDataSource") DataSource dataSource){
//        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(dataSource);
//        writer.setSql("INSERT INTO transaction_batch (id, am" +
//                "ount, timesstamp, receiver_id, sender_id) " +
//                "VALUES (:id, :amount, :timesstamp, :receiver_id, :sender_id)");
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        return writer;
//    }
}
