package com.fabrik.api.domain;

public record TransactionRecord (
    String transactionId,
    String operationId,
    String accountingDate,
    String valueDate,
    TransactionType transactionType,
    double amount,
    String currency,
    String description)
{}
