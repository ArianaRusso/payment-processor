package br.com.arianarusso.paymentprocessor.batch.config.writer;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
@Configuration
public class FileWriterConfig {

    @Bean
    public FlatFileItemWriter<Transaction> fileTransactionWriter(){
        return new FlatFileItemWriterBuilder<Transaction>()
                .name("fileTransactionWriter")
                .resource(new FileSystemResource("./file/transactions.txt"))
                .delimited()
                .names("id", "amount", "timesstamp", "receiver_id", "sender_id")
                .build();
    }
}
