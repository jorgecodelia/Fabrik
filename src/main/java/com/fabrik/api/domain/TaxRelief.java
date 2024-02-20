package com.fabrik.api.domain;

public record TaxRelief (
    String taxReliefId,
    boolean isCondoUpgrade,
    String creditorFiscalCode,
    String beneficiaryType,
    Beneficiary naturalPersonBeneficiary,
    Beneficiary legalPersonBeneficiary
){}
