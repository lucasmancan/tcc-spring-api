package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.PersonType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {

    private Long code;
    private String email;

    private String password;


}
