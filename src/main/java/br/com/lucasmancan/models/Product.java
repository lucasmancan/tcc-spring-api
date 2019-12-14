package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name="products")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "category"})
@ToString(callSuper=false, exclude = {"account", "category"})
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
	@JsonIgnore
	private Status status;

	private Long code;

	private BigDecimal price;

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
