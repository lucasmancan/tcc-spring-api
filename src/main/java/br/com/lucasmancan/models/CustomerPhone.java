package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "customers_phones")
@EqualsAndHashCode(callSuper = false, exclude = {"customer", "creationAppUser"})
@ToString(callSuper = false, exclude = {"customer", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPhone implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "area_code")
    private String areaCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 3)
    private ContactType type;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_number")
    private String phoneNumber;
	
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
