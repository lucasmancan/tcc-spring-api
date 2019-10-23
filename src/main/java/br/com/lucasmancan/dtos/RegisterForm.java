package br.com.lucasmancan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

    private Long accountId;
    private String accountName;
    private String name;
    private String document;
    private String email;
    private String password;
    private String passwordConfirmation;


}
