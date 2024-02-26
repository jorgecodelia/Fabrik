package com.fabrik.api.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private String date;
    private double balance; //NOPMD
    private double availableBalance;
    private String currency;
}
