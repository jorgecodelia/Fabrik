package com.fabrik.api.common.domain;

public record TaxRelief (
    String taxReliefId,
    boolean isCondoUpgrade,
    String creditorFiscalCode,
    String beneficiaryType,
    Beneficiary naturalPersonBeneficiary,
    Beneficiary legalPersonBeneficiary
){}
