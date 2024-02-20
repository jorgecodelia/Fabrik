package com.fabrik.api.domain;

public record Amount (
    int debtorAmount,
    String debtorCurrency,
    int creditorAmount,
    String creditorCurrency,
    String creditorCurrencyDate,
    int exchangeRate
){}
