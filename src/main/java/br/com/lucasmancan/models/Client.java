package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="clients")
@EqualsAndHashCode(callSuper=false, exclude = {"emails", "phones", "addresses", "account"})
@ToString(callSuper=false, exclude = {"emails", "phones", "addresses", "account"})
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

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

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "client")
	private Set<ClientEmail> emails = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "client")
	private Set<ClientPhone> phones = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "client")
	private Set<ClientAddress> addresses = new HashSet<>();
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creation_user_id")
    private AppUser creationAppUser;
	
}
