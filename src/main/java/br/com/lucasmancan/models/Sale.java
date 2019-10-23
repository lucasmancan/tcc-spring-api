package br.com.lucasmancan.models;

import br.com.lucasmancan.dtos.AccountSummary;
import br.com.lucasmancan.dtos.AmountByItem;
import br.com.lucasmancan.dtos.SaleDTO;
import br.com.lucasmancan.dtos.sql.AmountByEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "sales")
@EqualsAndHashCode(callSuper = false, of={"id"})
@ToString(callSuper = false, of={"id"})
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SqlResultSetMapping(
        name="getSaleResult",
        classes={
                @ConstructorResult(
                        targetClass= SaleDTO.class,
                        columns={
                                @ColumnResult(name="code", type= Long.class),
                                @ColumnResult(name="status", type=Status.class),
                                @ColumnResult(name="employee", type=String.class),
                                @ColumnResult(name="otherExpenses", type=BigDecimal.class),
                                @ColumnResult(name="discount", type=BigDecimal.class),
                                @ColumnResult(name="grossAmount", type=BigDecimal.class),
                                @ColumnResult(name="amount", type=BigDecimal.class),
                                @ColumnResult(name="updatedAt", type= Date.class)

                        }
                )
        }
)
@SqlResultSetMapping(
        name="fetchAmountByProductResult",
        classes={
                @ConstructorResult(
                        targetClass= AmountByEntity.class,
                        columns={
                                @ColumnResult(name="name", type= String.class),
                                @ColumnResult(name="amount", type=BigDecimal.class),
                                @ColumnResult(name="quantity", type=BigInteger.class),
                                @ColumnResult(name="code", type=Long.class),
                        }
                )
        }
)

@SqlResultSetMapping(
        name="fetchAmountByCustomerResult",
        classes={
                @ConstructorResult(
                        targetClass= AmountByEntity.class,
                        columns={
                                @ColumnResult(name="name", type= String.class),
                                @ColumnResult(name="amount", type=BigDecimal.class),
                                @ColumnResult(name="quantity", type=BigInteger.class),
                                @ColumnResult(name="code", type=Long.class),
                        }
                )
        }
)


//@NamedNativeQuery(name="Sale.getSummary", query="select sum(amount) as amount,sum(discount) as discount, sum(gross_amount) as grossAmount, sum(other_expenses) as otherExpenses, count(*) as total  from sales where account_id =:accountId group by account_id", resultSetMapping="getSummaryResult")
@NamedNativeQuery(name="Sale.fetchAll", query="SELECT s.code as code ,\n" +
        "       s.status as status,\n" +
        "       u.name as employee,\n" +
        "       s.other_expenses as otherExpenses,\n" +
        "       s.discount as discount,\n" +
        "       s.gross_amount as grossAmount,\n" +
        "       s.amount as amount,\n " +
        "       s.updated_at as updatedAt \n" +
        "FROM sales s\n" +
        "    join accounts a on a.id = s.account_id\n" +
        "    join users u on u.id = s.employee_id\n" +
        "    left join sales_customers sc on sc.sale_id = s.id\n" +
        "    left join customers c on c.id = sc.customer_id\n" +
        "WHERE s.account_id =:accountId and\n" +
        "    (:status is null or s.status =:status) and\n" +
        "    (:employee is null or u.name =:employee) and\n" +
        "    (:customerName is null or c.name =:customerName) and\n" +
        "    ((:lower is null or s.amount >=:lower) and (:upper is null or s.amount <=:upper))", resultSetMapping = "getSaleResult")
@NamedNativeQuery(name="Sale.fetchAmountByProduct", query="select p.name, sum(si.amount) as amount, count(*) as quantity, p.code as code from sales s\n" +
        "    join sales_itens si on si.sale_id = s.id\n" +
        "    join products p on p.id = si.product_id\n" +
        "    where s.account_id =:accountId\n" +
        "    group by p.id", resultSetMapping = "fetchAmountByProductResult")
@NamedNativeQuery(name="Sale.fetchAmountByCustomer", query="select p.name, sum(s.amount) as amount, count(*) as quantity, p.code as code from sales s\n" +
        "            join sales_customers sc on sc.sale_id = s.id\n" +
        "            join customers p on p.id = sc.customer_id\n" +
        "where s.account_id =:accountId\n" +
        "group by p.id", resultSetMapping = "fetchAmountByCustomerResult")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sale extends SaleEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private AppUser employee;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnoreProperties(value = {"sale", "hibernateLazyInitializer", "handler"})
    @OneToMany( mappedBy = "sale", fetch = FetchType.LAZY)
    private Set<SaleItem> items = new HashSet<SaleItem>();

    @JsonIgnoreProperties(value = {"sale", "hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private Set<SaleCustomer> customers = new HashSet<SaleCustomer>();

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Temporal(TemporalType.TIMESTAMP)

    private Date createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
@Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


}
