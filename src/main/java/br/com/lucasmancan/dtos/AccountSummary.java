package br.com.lucasmancan.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSummary {
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal grossAmount;
    private BigDecimal otherExpenses;
    private BigInteger total;
}
