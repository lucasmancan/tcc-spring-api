package br.com.lucasmancan.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="accounts")
@EqualsAndHashCode(callSuper=false, exclude = {"admin", "configuration"})
@ToString(callSuper=false, exclude = {"admin", "configuration"})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="admin_id")
	private AppUser admin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="configuration_id")
	private AccountConfiguration configuration;
	
	private Boolean active;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
}
