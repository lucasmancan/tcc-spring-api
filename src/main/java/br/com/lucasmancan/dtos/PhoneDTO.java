package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.ContactType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    @NotNull
    public String phoneNumber;

    @NotNull
    public String areaCode;

    @NotNull
    public String countryCode;

    @NotNull
    public ContactType contactType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date updatedAt;
}
