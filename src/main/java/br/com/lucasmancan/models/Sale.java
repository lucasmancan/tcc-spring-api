package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "sales")
@EqualsAndHashCode(callSuper = false, exclude = {"account", "items"})
@ToString(callSuper = false, exclude = {"account", "items"})
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sale implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private AppUser employee;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnoreProperties(value = {"sale"})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleItem> items = new HashSet<SaleItem>();

    private Long code;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    private AppUser updatedUser;

    @Column(name = "other_expenses")
    private BigDecimal otherExpenses;

    private BigDecimal discount;

    @Column(name = "gross_amount")
    private BigDecimal grossAmount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PreUpdate
    public void beforeUpdate(final Sale sale) {
        sale.setUpdatedAt(LocalDateTime.now());
    }

    @PrePersist
    public void beforePersist(final Sale sale) {

        sale.setCreatedAt(LocalDateTime.now());

        if (sale.getStatus() == null) {
            sale.setStatus(Status.PENDENTE);
        }

        if (sale.getDiscount() == null) {
            sale.setDiscount(BigDecimal.ZERO);
        }

        if (sale.getOtherExpenses() == null) {
            sale.setOtherExpenses(BigDecimal.ZERO);
        }

        if (sale.getGrossAmount() == null) {
            sale.setGrossAmount(BigDecimal.ZERO);
        }

        if (sale.getAmount() == null) {
            sale.setAmount(BigDecimal.ZERO);
        }

        sale.setUpdatedAt(LocalDateTime.now());
    }

}
