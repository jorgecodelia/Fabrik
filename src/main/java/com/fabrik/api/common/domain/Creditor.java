package com.fabrik.api.common.domain;

public record Creditor (
    String name,
    Account account,
    Address address
){}
