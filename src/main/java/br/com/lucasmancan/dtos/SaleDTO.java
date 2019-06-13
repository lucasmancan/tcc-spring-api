package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY )
public class SaleDTO implements Serializable {

    private Long code;
    private Sale.SaleState state;
    private BigDecimal otherExpenses;
    private BigDecimal discount;
    private BigDecimal grossAmount;
    private BigDecimal amount;
    private Date createdAt;
    private Date updatedAt;

    public SaleDTO(Sale sale) {
        this.code = sale.getCode();
        this.createdAt = sale.getCreatedAt();
        this.updatedAt = sale.getUpdatedAt();
        this.discount = sale.getDiscount();
        this.otherExpenses = sale.getOtherExpenses();
        this.grossAmount = sale.getGrossAmount();
        this.amount = sale.getAmount();
        this.state = sale.getState();
    }
}
