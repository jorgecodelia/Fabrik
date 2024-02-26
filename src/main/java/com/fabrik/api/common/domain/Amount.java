package com.fabrik.api.common.domain;

public record Amount (
    int debtorAmount,
    String debtorCurrency,
    int creditorAmount,
    String creditorCurrency,
    String creditorCurrencyDate,
    int exchangeRate
){}
