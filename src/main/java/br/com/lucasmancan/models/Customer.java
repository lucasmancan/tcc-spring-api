package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "customers")
@EqualsAndHashCode(callSuper=false, exclude = {"emails", "phones", "addresses", "account"})
@ToString(callSuper=false, exclude = {"emails", "phones", "addresses", "account"})
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="account_id")
	private Account account;
	
	private String name;
	
	private Long code;
		
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 2)
	private PersonType type;

	@Column(name = "document", length = 14)
	private String document;
	
	private Boolean active;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerEmail> emails = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerPhone> phones = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "customer")
    private Set<CustomerAddress> addresses = new HashSet<>();


	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name="created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)

	private Date createdAt;
	
	@Column(name="updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)

	private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creation_user_id")
    private AppUser creationAppUser;
	
}
