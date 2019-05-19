package br.com.lucasmancan.models;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="addresses")
@EqualsAndHashCode(callSuper=false, exclude = {"country", "state"})
@ToString(callSuper=false, exclude = {"country", "state"})
public class Address implements Serializable{
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="street")
	private String street;
	
	@Column(name="number")
	private String number;
	
	@Column(name="zip_code", length = 10)
	private Integer zipCode;

	@Column(name="city", length = 255)
	private String city;

	@Enumerated(EnumType.STRING)
	@Column(name="type", length = 3)
	private ContactType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="state_id", referencedColumnName = "id")
	private State state;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="country_id")
	private Country country;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
