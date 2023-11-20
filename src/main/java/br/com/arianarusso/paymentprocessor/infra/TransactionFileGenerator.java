package br.com.arianarusso.paymentprocessor.infra;

import br.com.arianarusso.paymentprocessor.batch.model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionFileGenerator {

    public static void main(String[] args) {
        generateTestFile();
    }

    private static void generateTestFile() {
        try (FileWriter writer = new FileWriter("transactions.txt")) {
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = generateRandomTransaction(i);
                writer.write(transactionToString(transaction));
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Transaction generateRandomTransaction(int index) {
        return Transaction.builder()
                .id(UUID.randomUUID())
                .amount(new BigDecimal(Math.random() * 1000))
                .timesstamp(LocalDateTime.now())
                .receiver_id(UUID.randomUUID())
                .sender_id(UUID.randomUUID())
                .build();
    }

    private static String transactionToString(Transaction transaction) {
        return String.format("%s,%s,%s,%s,%s",
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTimesstamp(),
                transaction.getReceiver_id(),
                transaction.getSender_id());
    }
}
