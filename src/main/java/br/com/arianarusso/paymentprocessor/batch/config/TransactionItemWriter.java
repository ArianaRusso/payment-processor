package br.com.arianarusso.paymentprocessor.batch.config;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TransactionItemWriter {

    private List<Transaction> processedTransactions = new ArrayList<>();

    public TransactionItemWriter(List<Transaction> processedTransactions) {
        this.processedTransactions = processedTransactions;
    }

    @Bean
    public ItemWriter<Transaction> writer() {
        return itens -> {
            itens.forEach(processedTransactions::add);
        };
    }

    public List<Transaction> getWrittenItems() {
        return processedTransactions;
    }
}
