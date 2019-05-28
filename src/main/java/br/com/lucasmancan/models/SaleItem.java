package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;


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
