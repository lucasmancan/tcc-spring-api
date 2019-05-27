package br.com.lucasmancan.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false, exclude = {"account", "mainAddress", "mainPhone", "emails", "phones", "addresses"})
@ToString(callSuper = false, exclude = {"account", "mainAddress", "mainPhone", "emails", "phones", "addresses"})
@AllArgsConstructor
@NoArgsConstructor
public class AppUser  implements UserDetails, Serializable {


	public AppUser(String username, String password){
		this.username = username;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		var adminList = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
		var userList = AuthorityUtils.createAuthorityList("ROLE_USER");

		return this.admin ? adminList : userList;
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

	private String password;

	@Column(name = "username")
	private String username;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_phone_id")
	private Phone mainPhone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_address_id")
	private Address mainAddress;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
	private Set<UserEmail> emails = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
	private Set<UserPhone> phones = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
	private Set<UserAddress> addresses = new HashSet<>();
	
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
