package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY )
public class SaleDTO implements Serializable {

    private Long code;
    private Status status;
    private String employee;
    private BigDecimal otherExpenses;

    private BigDecimal discount;

    private BigDecimal grossAmount;

    private BigDecimal amount;


private LocalDateTime updatedAt;

    public SaleDTO(Sale sale) {
        this.setCode(sale.getCode());
        this.setStatus(sale.getStatus());
        this.setOtherExpenses(sale.getOtherExpenses());
        this.setDiscount(sale.getDiscount());
        this.setAmount(sale.getAmount());
        this.setGrossAmount(sale.getGrossAmount());
        this.setUpdatedAt(sale.getUpdatedAt());

        if(sale.getEmployee() != null){
            this.setEmployee(sale.getEmployee().getName());
        }
    }
}
