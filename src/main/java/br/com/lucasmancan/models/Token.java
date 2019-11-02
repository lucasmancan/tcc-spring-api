package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tokens")
@EqualsAndHashCode(callSuper=false, of = {"id"})
@ToString(callSuper=false,  of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="customer_id")
	private Customer customer;

	private Boolean active;
	
	@Column(name="created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name="expires_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiresAt;
	
	@Column(name="updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(name="used_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@Temporal(TemporalType.TIMESTAMP)
	private Date usedAt;
}
