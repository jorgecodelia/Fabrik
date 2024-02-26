package com.fabrik.api.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private TransactionType transactionType;
    private double amount;
    private String currency;
    private String description;
}
