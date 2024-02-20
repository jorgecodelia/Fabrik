package com.fabrik.api.domain;

public record Balance (
    String date,
    double balance,
    double availableBalance,
    String currency
){}
