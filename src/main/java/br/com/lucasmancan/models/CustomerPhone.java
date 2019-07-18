package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="clients_phones")
@EqualsAndHashCode(callSuper=false, exclude = {"client", "phone", "creationAppUser"})
@ToString(callSuper=false, exclude = {"client", "phone","creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPhone implements Serializable {

	@Id
	@EmbeddedId
    private CustomerPhonePK id;
	
	@MapsId("clientId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="client_id")
    private Customer client;

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
