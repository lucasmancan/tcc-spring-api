package br.com.lucasmancan.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="sales")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "items", "appUser"})
@ToString(callSuper=false, exclude = {"account", "items", "appUser"})
@AllArgsConstructor
@NoArgsConstructor
public class Sale  implements Serializable{

	enum SaleState{
		PEN, FIN, CAN
	}
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="account_id")
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser appUser;
	
	private Long code;

	
	@Enumerated(EnumType.STRING)
	@Column(name = "state", length = 3)
	private SaleState state;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SaleItem> items = new HashSet<>();

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

}
