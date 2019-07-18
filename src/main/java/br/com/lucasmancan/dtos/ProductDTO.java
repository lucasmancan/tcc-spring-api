package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private Long code;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    @NotNull
    private ProductCategoryDTO category;

    public ProductDTO(Product product) {
        this.code = product.getCode();
        this.updatedAt = product.getUpdatedAt();
        this.name = product.getName();
        this.description = product.getDescription();
    }
}
