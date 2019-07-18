package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.ContactType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    @NotNull
    private String street;

    @NotNull
    private String number;

    @NotNull
    private Integer zipCode;

    @NotNull
    private String city;

    @NotNull
    private ContactType type;

    @NotNull
    private String state;

    @NotNull
    private String country;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;
}
