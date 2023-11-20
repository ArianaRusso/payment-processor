package br.com.arianarusso.paymentprocessor.batch.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Transaction {

    private UUID id;

    private BigDecimal amount;

    private LocalDateTime timesstamp;

    private UUID receiver_id;

    private UUID sender_id;

}