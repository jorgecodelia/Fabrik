package com.fabrik.api.domain;

public record Fee (
    String feeCode,
    String description,
    double amount,
    String currency
){}
