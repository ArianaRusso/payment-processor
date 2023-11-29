package br.com.arianarusso.paymentprocessor.batch.config.writer;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConsoleWriterConfig {

    private List<Transaction> processedTransactions = new ArrayList<>();

    public ConsoleWriterConfig(List<Transaction> processedTransactions) {
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



}
