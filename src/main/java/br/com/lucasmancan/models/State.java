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
@Table(name="states")
@EqualsAndHashCode(callSuper=false, exclude = {"country"})
@ToString(callSuper=false,exclude = {"country"})
@AllArgsConstructor
@NoArgsConstructor
public class State implements Serializable{

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="initials", length=2)
	private String initials;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="country_id")
	private Country country;
	
	@Column(name="name")
	private String name;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
