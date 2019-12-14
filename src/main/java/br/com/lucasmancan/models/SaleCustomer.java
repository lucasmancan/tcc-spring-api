package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;@Data
@Entity
@Table(name = "sales_customers")
@EqualsAndHashCode(callSuper = false, exclude = {"customer", "sale"})
@ToString(callSuper = false, exclude = {"customer", "sale"})
@AllArgsConstructor
@NoArgsConstructor
public class SaleCustomer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")


    private LocalDateTime createdAt;

    @Column(name = "updated_at")


    private LocalDateTime updatedAt;

}
