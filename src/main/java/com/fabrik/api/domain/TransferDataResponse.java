package com.fabrik.api.domain;

import java.util.ArrayList;
import java.util.Date;

public record TransferDataResponse (
    String moneyTransferId,
    String status,
    String direction,
    Creditor creditor,
    Debtor debtor,
    String cro,
    String uri,
    String trn,
    String description,
    Date createdDatetime,
    Date accountedDatetime,
    String debtorValueDate,
    String creditorValueDate,
    Amount amount,
    boolean isUrgent,
    boolean isInstant,
    String feeType,
    String feeAccountId,
    ArrayList<Fee> fees,
    boolean hasTaxRelief
){}