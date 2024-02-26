package com.fabrik.api.common.domain;

public record Fee (
    String feeCode,
    String description,
    double amount,
    String currency
){}
