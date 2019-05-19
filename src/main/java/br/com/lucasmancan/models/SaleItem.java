package br.com.lucasmancan.models;

import java.io.Serializable;
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
@Table(name="sales_itens")
@EqualsAndHashCode(callSuper=false, exclude = {"product"})
@ToString(callSuper=false, exclude = {"product"})
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem  implements Serializable{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="product_id")
	private Product product;
	
	@Column(name="other_expenses")
	private BigDecimal otherExpenses;
	
	@Column(name="unitary")
	private BigDecimal unitary;
	
	@Column(name="quantity")
	private Integer quantity;
	
	private BigDecimal discount;

	@Column(name="total_gross")
	private BigDecimal totalGross;
	
	@Column(name="total_liquid")
	private BigDecimal totalLiquid;
		
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
