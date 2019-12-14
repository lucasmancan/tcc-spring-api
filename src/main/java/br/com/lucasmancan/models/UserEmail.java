package br.com.lucasmancan.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name="users_emails")
@EqualsAndHashCode(callSuper = false, exclude = {"appUser", "email"})
@ToString(exclude = {"appUser", "email"})
@AllArgsConstructor
@NoArgsConstructor
public class UserEmail implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="user_id")
	private AppUser appUser;

	@Column(name = "email")
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 3)
	private ContactType type;
	
	@Column(name="created_at")

	private LocalDateTime createdAt;
	
	@Column(name="updated_at")

	private LocalDateTime updatedAt;

}
