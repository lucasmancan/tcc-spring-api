package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private Long code;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public ProductDTO(Product product) {
        this.code = product.getCode();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.name = product.getName();
        this.description = product.getDescription();
    }
}
