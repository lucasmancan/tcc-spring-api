package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="products_categories")
@EqualsAndHashCode(callSuper = false, exclude = {"account", "parentCategory", "creationAppUser"})
@ToString(callSuper=false, exclude = {"account", "parentCategory", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory  implements Serializable{

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
	@JoinColumn( name ="parent_id")
	private ProductCategory parentCategory;
	
	private String name;
	
	private String description;

	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
