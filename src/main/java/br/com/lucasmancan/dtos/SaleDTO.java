package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Sale;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private List<SaleItemDTO> items = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public SaleDTO(Sale sale) {
        this.setCode(sale.getCode());
        this.setStatus(sale.getStatus());
        this.setOtherExpenses(sale.getOtherExpenses());
        this.setDiscount(sale.getDiscount());
        this.setAmount(sale.getAmount());
        this.setGrossAmount(sale.getGrossAmount());
        this.setUpdatedAt(sale.getUpdatedAt());

        if (!CollectionUtils.isEmpty(sale.getItems())) {

            ModelMapper mapper = new ModelMapper();

            for (SaleItem saleItem : sale.getItems()) {

                final SaleItemDTO saleItemDTO = new SaleItemDTO();
                final ProductDTO productDTO = mapper.map(saleItem.getProduct(), ProductDTO.class);

                saleItemDTO.setProduct(productDTO);
                saleItemDTO.setAmount(saleItem.getAmount());
                saleItemDTO.setStatus(saleItem.getStatus());
                saleItemDTO.setGrossAmount(saleItem.getGrossAmount());
                saleItemDTO.setDiscount(saleItem.getDiscount());
                saleItemDTO.setOtherExpenses(saleItem.getOtherExpenses());
                saleItemDTO.setUnitary(saleItem.getUnitary());

                this.getItems().add(saleItemDTO);
            }
        }
    }
}
