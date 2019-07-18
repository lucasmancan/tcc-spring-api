package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

	@Column(name="other_expenses")
	private BigDecimal otherExpenses;
	
	@Column(name="unitary")
	private BigDecimal unitary;
	
	@Column(name="quantity")
	private Integer quantity;
	
	private BigDecimal discount;

	@Column(name = "gross_amount")
	private BigDecimal grossAmount;

	@Column(name = "amount")
	private BigDecimal amount;
		
	@Column(name="created_at")
    private LocalDateTime createdAt;
	
	@Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void beforeUpdate(final SaleItem item) {
        item.setUpdatedAt(LocalDateTime.now());
    }

    @PrePersist
    public void beforePersist(final SaleItem item) {
        if (item.getDiscount() == null) {
            item.setDiscount(BigDecimal.ZERO);
        }

        if (item.getOtherExpenses() == null) {
            item.setOtherExpenses(BigDecimal.ZERO);
        }

        if (item.getGrossAmount() == null) {
            item.setGrossAmount(BigDecimal.ZERO);
        }

        if (item.getAmount() == null) {
            item.setAmount(BigDecimal.ZERO);
        }

        item.setStatus(Status.PENDENTE);

        item.setGrossAmount(item.getUnitary()
                .multiply(new BigDecimal(item.getQuantity()))
                .add(item.getOtherExpenses()));

        item.setAmount(item.getGrossAmount().subtract(item.getDiscount()));
        item.setUpdatedAt(LocalDateTime.now());
    }

}
