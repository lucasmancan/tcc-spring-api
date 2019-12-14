package br.com.lucasmancan.dtos;

import br.com.lucasmancan.utils.AppUtils;
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

    public void setToken(String token){
        this.token = AppUtils.trim(token);
    }

    public void setPassword(String token){
        this.password = AppUtils.trim(token);
    }

    public void setConfirmation(String token){
        this.confirmation = AppUtils.trim(token);
    }


    public boolean matchPassword(){
        return password.equals(confirmation);
    }
}
