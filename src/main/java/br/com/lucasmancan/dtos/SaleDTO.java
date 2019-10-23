package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Sale;
import br.com.lucasmancan.models.SaleCustomer;
import br.com.lucasmancan.models.SaleItem;
import br.com.lucasmancan.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;

import javax.persistence.Cacheable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date updatedAt;

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
