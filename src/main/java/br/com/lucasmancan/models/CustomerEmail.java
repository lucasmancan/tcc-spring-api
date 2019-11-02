package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;import java.util.Date;

@Data
@Entity
@Table(name = "customers_emails")
@EqualsAndHashCode(callSuper = false, exclude = {"customer", "creationAppUser"})
@ToString(callSuper = false, exclude = {"customer", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmail implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 3)
    private ContactType type;
	
	@Column(name="created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)

	private Date createdAt;
	
	@Column(name="updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)

	private Date updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")
	private AppUser creationAppUser;

}