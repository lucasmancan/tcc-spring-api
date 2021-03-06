package br.com.lucasmancan.dtos;

import br.com.lucasmancan.models.Account;
import br.com.lucasmancan.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserAuthentication implements Authentication {

    private Long id;
    private String email;
    private String password;
    private AppUser appUser;
    private Account account;
    private Collection<GrantedAuthority> authorities;
    private boolean isAuthenticated;

    public AppUserAuthentication(AppUser appUser) {
        this.id = appUser.getId();
        this.email = appUser.getUsername();
        this.password = appUser.getPassword();
        this.authorities = appUser.getAuthorities();
        this.appUser = appUser;
        this.account = appUser.getAccount();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.appUser.getName();
    }
}
