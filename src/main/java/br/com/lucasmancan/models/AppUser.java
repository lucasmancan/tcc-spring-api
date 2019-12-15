package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false, exclude = {"account", "emails", "phones", "addresses"})
@ToString(callSuper = false, exclude = {"account", "emails", "phones", "addresses"})
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable, UserDetails, Authentication {

    public AppUser(String username, String password) {
        this.email = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long code;

    private String name;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "cover_image")
    private String coverImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = {"admin"}, allowSetters = true)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 2)
    private PersonType type;

    @Column(name = "document", length = 14)
    private String document;

    @JsonIgnore
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
    private Set<UserEmail> emails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
    private Set<UserPhone> phones = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
    private Set<UserAddress> addresses = new HashSet<>();

    private Boolean active;

    private Boolean expired;

    @Column(name = "expired_at")

    private LocalDateTime expiredAt;

    @Column(name = "created_at")

    private LocalDateTime createdAt;

    @Column(name = "updated_at")

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "logged_at")

    private LocalDateTime loggedAt;

    @Transient
    @JsonIgnore
    private Collection<GrantedAuthority> authorities;


    @Override
	@JsonIgnore
	public Collection<GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        var adminList = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        var userList = AuthorityUtils.createAuthorityList("ROLE_USER");

        return  adminList;
    }

    @Override
	@JsonIgnore
	public Object getCredentials() {
        return null;
    }


    @Override
	@JsonIgnore
	public Object getDetails() {
        return null;
    }

    @Override
    @JsonIgnore

    public Object getPrincipal() {
        return this.email;
    }

    @Override
	@JsonIgnore
	public boolean isAuthenticated() {
        return true;
    }

    @Override
	@JsonIgnore
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
	public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }

    @Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
	@JsonIgnore
	public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.active;
    }


    @Override
	@JsonIgnore
	public String getName() {
        return this.username;
    }
}
