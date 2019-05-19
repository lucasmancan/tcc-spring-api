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
@Table(name="users_emails")
@EqualsAndHashCode(callSuper=false, exclude = {"client", "email"})
@ToString(callSuper=false, exclude = {"client", "email"})
@AllArgsConstructor
@NoArgsConstructor
public class UserEmail implements Serializable{

	@EmbeddedId
	private UserEmailPK id;
	
	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser appUser;

	@MapsId("emailId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="email_id")
	private Email email;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
