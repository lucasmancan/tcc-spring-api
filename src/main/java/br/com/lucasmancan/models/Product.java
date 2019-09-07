package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
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
    private LocalDateTime createdAt;
	
	@Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist() {
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void beforeUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

}
