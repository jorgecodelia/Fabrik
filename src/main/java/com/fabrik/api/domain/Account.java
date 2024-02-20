package com.fabrik.api.domain;

public record Account (
    String accountId,
    String iban,
    String abiCode,
    String cabCode,
    String countryCode,
    String internationalCin,
    String nationalCin,
    String account,
    String alias,
    String productName,
    String holderName,
    String activatedDate,
    String currency
){}
