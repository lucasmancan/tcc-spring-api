package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "customers_emails")
@EqualsAndHashCode(callSuper=false, exclude = {"client", "email","creationAppUser"})
@ToString(callSuper=false, exclude = {"client", "email", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmail implements Serializable {

	@Id
	@EmbeddedId
    private CustomerEmailPK id;
	
	@MapsId("clientId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="client_id")
    private Customer client;

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
