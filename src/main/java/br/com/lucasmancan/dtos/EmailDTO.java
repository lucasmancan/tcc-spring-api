package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.ContactType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    @NotNull
    private String email;

    @NotNull
    private ContactType contactType;

private LocalDateTime updatedAt;
}
