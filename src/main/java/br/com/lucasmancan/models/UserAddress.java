package br.com.lucasmancan.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name="users_addresses")
@EqualsAndHashCode(callSuper=false, exclude = {"appUser", "address"})
@ToString(callSuper=false, exclude = {"appUser", "address"})
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress implements Serializable{

	@EmbeddedId
	private UserAddressPK id;
	
	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser appUser;

	@MapsId("addressId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="address_id")
	private Address address;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
}
