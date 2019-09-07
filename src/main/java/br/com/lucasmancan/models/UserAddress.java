package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="users_addresses")
@EqualsAndHashCode(callSuper = false, exclude = {"appUser"})
@ToString(exclude = {"appUser"})
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser appUser;

	@Column(name = "street")
	private String street;

	@Column(name = "number")
	private String number;

	@Column(name = "zip_code", length = 10)
	private Integer zipCode;

	@Column(name = "city", length = 255)
	private String city;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 3)
	private ContactType type;


    private String state;

	private String country;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
}
