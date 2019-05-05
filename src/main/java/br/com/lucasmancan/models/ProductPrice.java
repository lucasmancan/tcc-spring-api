package br.com.lucasmancan.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name="products_prices")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "category", "creationUser"})
@ToString(callSuper=false, exclude = {"account", "parentCategory", "creationUser"})
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrice {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="product_id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")
	private User creationUser;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="start_validity")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startValidity;
	
	@Column(name="end_validity")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endValidity;

	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
