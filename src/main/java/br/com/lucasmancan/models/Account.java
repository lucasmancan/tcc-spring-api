package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

	@JsonIgnore
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"account"}, allowSetters = true)
	@JoinColumn( name ="admin_id")
	private AppUser admin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="configuration_id")
	private AccountConfiguration configuration;

	private Boolean active;

	@Column(name="created_at")

	private LocalDateTime createdAt;

	@Column(name="updated_at")

	private LocalDateTime updatedAt;

}
