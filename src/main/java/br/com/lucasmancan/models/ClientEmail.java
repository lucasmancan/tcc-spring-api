package br.com.lucasmancan.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name="clients_emails")
@EqualsAndHashCode(callSuper=false, exclude = {"client", "email","creationAppUser"})
@ToString(callSuper=false, exclude = {"client", "email", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class ClientEmail implements Serializable{

	@Id
	@EmbeddedId
	private ClientEmailPK id;
	
	@MapsId("clientId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="client_id")
	private Client client;

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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")
	private AppUser creationAppUser;

}
