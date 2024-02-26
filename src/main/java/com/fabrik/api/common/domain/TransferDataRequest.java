package com.fabrik.api.common.domain;

public record TransferDataRequest (
    Creditor creditor,
    String executionDate,
    String uri,
    String description,
    int amount,
    String currency,
    boolean isUrgent,
    boolean isInstant,
    String feeType,
    String feeAccountId,
    TaxRelief taxRelief
){}
