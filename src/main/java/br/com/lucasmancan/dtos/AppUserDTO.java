package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.PersonType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {

    private Long code;

    @NotNull
    private String name;

    private String profileImage;

    private String coverImage;

    private PersonType type;

    @NotNull
    private String document;

    @NotNull
    private String username;

    private String password;
    private Boolean active;
    private Boolean admin;
    private Boolean expired;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expiredAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime loggedAt;
}
