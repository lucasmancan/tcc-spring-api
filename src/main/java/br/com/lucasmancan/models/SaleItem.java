package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sales_itens")
@EqualsAndHashCode(callSuper = false, exclude = {"product"})
@ToString(callSuper = false, exclude = {"product"})
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "other_expenses")
    private BigDecimal otherExpenses = BigDecimal.ZERO;

    @Column(name = "unitary")
    private BigDecimal unitary = BigDecimal.ZERO;

    @Column(name = "quantity")
    private Integer quantity = 0;

    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "gross_amount")
    private BigDecimal grossAmount = BigDecimal.ZERO;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void beforeUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

    @PrePersist
    public void beforePersist() {
        if (this.getDiscount() == null) {
            this.setDiscount(BigDecimal.ZERO);
        }

        if (this.getOtherExpenses() == null) {
            this.setOtherExpenses(BigDecimal.ZERO);
        }

        if (this.getGrossAmount() == null) {
            this.setGrossAmount(BigDecimal.ZERO);
        }

        if (this.getAmount() == null) {
            this.setAmount(BigDecimal.ZERO);
        }
        if (this.getUnitary() == null) {
            this.setUnitary(BigDecimal.ZERO);
        }

        if (this.getQuantity() == null) {
            this.setQuantity(0);
        }

        if (this.getStatus() == null) {
            this.setStatus(Status.PENDENTE);
        }

        this.setGrossAmount(this.getUnitary()
                .multiply(new BigDecimal(this.getQuantity()))
                .add(this.getOtherExpenses()));

        this.setAmount(this.getGrossAmount().subtract(this.getDiscount()));
        this.setUpdatedAt(LocalDateTime.now());
    }

}
