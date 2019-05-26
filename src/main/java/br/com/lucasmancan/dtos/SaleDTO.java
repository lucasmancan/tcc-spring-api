package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.AppUser;
import br.com.lucasmancan.models.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO  implements Serializable{

    public SaleDTO(Sale sale){
        this.code = sale.getCode();
        this.createdAt = sale.getCreatedAt();
        this.updatedAt = sale.getUpdatedAt();
        this.discount = sale.getDiscount();
        this.otherExpenses = sale.getOtherExpenses();
        this.totalGross = sale.getTotalGross();
        this.totalLiquid = sale.getTotalLiquid();
        this.state = sale.getState();
    }

    private Long code;

    private Sale.SaleState state;

    private BigDecimal otherExpenses;

    private BigDecimal discount;

    private BigDecimal totalGross;

    private BigDecimal totalLiquid;

    private Date createdAt;

    private Date updatedAt;
}
