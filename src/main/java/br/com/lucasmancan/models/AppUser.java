package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false, exclude = {"account", "mainAddress", "mainPhone"})
@ToString(callSuper = false, exclude = {"account", "mainAddress", "mainPhone"})
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable, UserDetails, Authentication {

	public AppUser(String username, String password){
		this.username = username;
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
	@JoinColumn( name ="account_id")
	private Account account;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 2)
	private PersonType type;

	@Column(name = "document", length = 14)
	private String document;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

	@Column(name = "username")
	private String username;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
	private Set<UserEmail> emails = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
	private Set<UserPhone> phones = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
	private Set<UserAddress> addresses = new HashSet<>();
//
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "creationAppUser")
	private Set<UserPermissions> permissions = new HashSet<>();*/

	private Boolean active;
	
	private Boolean admin;

	private Boolean expired;

	@Column(name = "expired_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredAt;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(name = "logged_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loggedAt;

    @Override
	@Transient
    public Collection<GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        var adminList = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        var userList = AuthorityUtils.createAuthorityList("ROLE_USER");

        return this.admin ? adminList : userList;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.active;
	}
	
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return this.admin;
	}

}
