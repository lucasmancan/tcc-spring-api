package br.com.lucasmancan.models;

import ch.qos.logback.core.net.server.Client;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="clients_addresses")
@EqualsAndHashCode(callSuper=false, exclude = {"client", "address", "creationAppUser"})
@ToString(callSuper=false, exclude = {"client", "address", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddress implements Serializable {

	@EmbeddedId
    private CustomerAddressPK id;
	
	@MapsId("clientId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="client_id", nullable = false, referencedColumnName = "id")
	private Client client;

	@MapsId("addressId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="address_id", nullable = false, referencedColumnName = "id")
	private Address address;
	
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
