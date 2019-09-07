package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.PersonType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    @NotNull
    private String name;
    private Long code;

    @NotNull
    private PersonType type;
    private String document;
    private Boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    private List<EmailDTO> emails;

    private List<PhoneDTO> phones;

    private List<AddressDTO> addresses;

}

