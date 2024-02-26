package com.fabrik.api.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountId;
    private String iban;
    private String abiCode;
    private String cabCode;
    private String countryCode;
    private String internationalCin;
    private String nationalCin;
    private String account; //NOPMD
    private String alias;
    private String productName;
    private String holderName;
    private String activatedDate;
    private String currency;
    private String accountCode;
    private String bicCode;
}
