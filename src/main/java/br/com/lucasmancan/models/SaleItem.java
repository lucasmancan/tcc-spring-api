package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;@Data
@Entity
@Table(name = "sales_itens")
@EqualsAndHashCode(callSuper = false, exclude = {"product", "sale", "updatedUser"})
@ToString(callSuper = false, exclude = {"product","sale", "updatedUser"})
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem extends SaleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})

    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "unitary")
    private BigDecimal unitary = BigDecimal.ZERO;

    @Column(name = "quantity")
    private Integer quantity = 0;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    private AppUser updatedUser;

    @Column(name = "other_expenses")
    private BigDecimal otherExpenses = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "gross_amount")
    private BigDecimal grossAmount = BigDecimal.ZERO;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)

    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)

    private Date updatedAt;
}
