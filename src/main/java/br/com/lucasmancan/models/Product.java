package br.com.lucasmancan.models;

import java.io.Serializable;
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

@Data
@Entity
@Table(name="products")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "category", "creationAppUser"})
@ToString(callSuper=false, exclude = {"account", "category", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class Product  implements Serializable{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="account_id")
	private Account account;
	
	
	private Long code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")
	private AppUser creationAppUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private ProductCategory category;
	
	private String name;
	
	private String description;

	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
