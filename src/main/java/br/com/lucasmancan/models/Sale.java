package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="sales")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "items", "appUser"})
@ToString(callSuper=false, exclude = {"account", "items", "appUser"})
@AllArgsConstructor
@NoArgsConstructor
public class Sale  implements Serializable{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private AppUser employee;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="account_id")
	private Account account;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items;
	
	private Long code;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "state", length = 3)
	private SaleState state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    private AppUser updatedUser;

	@Column(name="other_expenses")
	private BigDecimal otherExpenses;
	
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

    public enum SaleState {
        PEN, FIN, CAN
    }

}
