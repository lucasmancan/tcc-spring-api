package br.com.lucasmancan.dtos.sql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.NamedNativeQuery;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountByEntity {
    private String name;
    private BigDecimal amount;
    private BigInteger quantity;
    private Long code;

}