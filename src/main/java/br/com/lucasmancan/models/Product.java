package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;@Data
@Entity
@Table(name="products")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "category", "creationAppUser"})
@ToString(callSuper=false, exclude = {"account", "category", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class Product  implements Serializable{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="account_id")
	private Account account;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	private Long code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")

	private AppUser creationAppUser;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")

	private ProductCategory category;
	
	private String name;
	
	private String description;

	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)

	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)

	private Date updatedAt;

    @PrePersist
    public void beforePersist() {
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }

    @PreUpdate
    public void beforeUpdate() {
        this.setUpdatedAt(new Date());
    }

}
