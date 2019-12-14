package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemDTO {
    @NotNull
    private ProductDTO product;
    private BigDecimal otherExpenses;
    @NotNull
    private BigDecimal unitary;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal discount;
    @NotNull
    private BigDecimal grossAmount;

    private Status status;

    @NotNull
    private BigDecimal amount;

private LocalDateTime updatedAt;
}
