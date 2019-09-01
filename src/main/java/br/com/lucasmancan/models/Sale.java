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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sale", fetch = FetchType.LAZY)
    private Set<SaleItem> items = new HashSet<SaleItem>();

    private Long code;

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
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PreUpdate
    public void beforeUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

    @PrePersist
    public void beforePersist() {

        this.setCreatedAt(LocalDateTime.now());

        if (this.getStatus() == null) {
            this.setStatus(Status.PENDENTE);
        }

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

        this.setUpdatedAt(LocalDateTime.now());
    }

}
