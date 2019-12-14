package br.com.lucasmancan.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO implements Serializable {

    private Long code;

    @NotNull
    private String name;

    @NotNull
    private String description;

private LocalDateTime updatedAt;
}
