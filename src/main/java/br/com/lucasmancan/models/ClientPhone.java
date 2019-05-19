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
@Table(name="clients_phones")
@EqualsAndHashCode(callSuper=false, exclude = {"client", "phone", "creationAppUser"})
@ToString(callSuper=false, exclude = {"client", "phone","creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class ClientPhone  implements Serializable{

	@Id
	@EmbeddedId
	private ClientPhonePK id;
	
	@MapsId("clientId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="client_id")
	private Client client;

	@MapsId("phoneId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="phone_id")
	private Phone phone;
	
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
