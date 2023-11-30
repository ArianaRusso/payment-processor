package br.com.arianarusso.paymentprocessor.batch.config.listener;

import br.com.arianarusso.paymentprocessor.batch.config.writer.ConsoleWriterConfig;
import br.com.arianarusso.paymentprocessor.batch.model.Transaction;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobTransactionListener implements JobExecutionListener {

    private static final String START_MESSAGE = "%s is beginning execution";
    private static final String END_MESSAGE = "%s has completed with the status %s";

    private List<Transaction> collectedTransactions = new ArrayList<>();

    @Autowired
    private ConsoleWriterConfig transactionItemWriter;

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.printf((START_MESSAGE) + "%n",
                jobExecution.getJobInstance().getJobName());
    }
    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        this.collectedTransactions= transactionItemWriter.getWrittenItems();

        writePrintConsole();
        writeMaxAmount();
        writeOrdered();
        writeFilterAmount();

        System.out.printf((END_MESSAGE) + "%n",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());
    }

    private void writePrintConsole() {
        collectedTransactions.forEach(System.out::println);
    }

    private void writeMaxAmount() {
        collectedTransactions
                .stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .ifPresent(transaction -> {
                    System.out.println("Transação com o valor máximo: " + transaction);
                });
    };

    private void writeOrdered() {
        //collectedTransactions.sort((t1, t2) -> t1.getAmount().compareTo(t2.getAmount()));
        collectedTransactions.sort(Comparator.comparing(Transaction :: getAmount));
        System.out.println("Transações ordenadas por valor:");
        collectedTransactions.forEach(System.out::println);
    };


    public void writeFilterAmount() {
        List<Transaction> filteredTransactions = collectedTransactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.valueOf(500)) > 0).toList();

        System.out.println("Transações com valor superior a 500:");
        filteredTransactions.forEach(System.out::println);
        //filteredTransactions.forEach(transaction -> System.out.println(transaction));
    };

}
