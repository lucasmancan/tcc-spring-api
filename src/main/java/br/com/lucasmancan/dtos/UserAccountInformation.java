package br.com.lucasmancan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountInformation {
    private String username;
    private String email;
    private String phone;
    private String password;
    private String confirmation;


    public boolean passwordMatch(){
        return this.password.equals(this.confirmation);
    }
}
