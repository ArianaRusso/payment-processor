package br.com.arianarusso.paymentprocessor.batch.config.reader;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class FileReaderConfig {
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
}
