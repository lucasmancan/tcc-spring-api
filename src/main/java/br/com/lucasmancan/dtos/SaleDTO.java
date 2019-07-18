package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY )
public class SaleDTO implements Serializable {

    private Long code;
    private Status status;
    private BigDecimal otherExpenses;

    @NotNull
    private BigDecimal discount;

    @NotNull
    private BigDecimal grossAmount;

    @NotNull
    private BigDecimal amount;

    private List<SaleItemDTO> items;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;
}
