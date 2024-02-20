package com.fabrik.api.domain;

public record Creditor (
    String name,
    Account account,
    Address address
){}
