package br.com.lucasmancan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordConfirmation {
    private String password;
    private String confirmation;

    private String token;
}
