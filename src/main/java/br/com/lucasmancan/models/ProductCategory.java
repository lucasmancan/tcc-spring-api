package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name="products_categories")
@EqualsAndHashCode(callSuper = false, exclude = {"account", "parentCategory"})
@ToString(exclude = {"account", "parentCategory"})
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

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@JsonIgnore
	private Status status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="parent_id")
	private ProductCategory parentCategory;
	
	private String name;
	
	private String description;

	@Column(name="created_at")

	private LocalDateTime createdAt;
	
	@Column(name="updated_at")

	private LocalDateTime updatedAt;

}
