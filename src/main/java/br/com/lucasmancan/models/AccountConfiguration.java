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

@Data
@Entity
@Table(name="accounts_configurations")
@EqualsAndHashCode(callSuper=false, exclude = {"account"})
@ToString(callSuper=false, exclude = {"account"})
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfiguration {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="language_id")
	private Language language;
	
	@Column(name="enabled_notification")
	private Boolean enabledNotification;
		
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
