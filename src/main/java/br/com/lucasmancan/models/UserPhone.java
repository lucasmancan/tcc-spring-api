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
@Table(name="users_phones")
@EqualsAndHashCode(callSuper=false, exclude = {"appUser", "phone"})
@ToString(callSuper=false, exclude = {"appUser", "phone"})
@AllArgsConstructor
@NoArgsConstructor
public class UserPhone implements Serializable{

	@EmbeddedId
	private UserPhonePK id;
	
	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser appUser;

	@MapsId("phoneId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="address_id")
	private Phone phone;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
}
