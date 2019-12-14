package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;import java.util.Date;

@Data
@Entity
@Table(name = "customers_emails")
@EqualsAndHashCode(callSuper = false, exclude = {"customer", "creationAppUser"})
@ToString(callSuper = false, exclude = {"customer", "creationAppUser"})
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmail implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 3)
    private ContactType type;
	
	@Column(name="created_at")


	private LocalDateTime createdAt;
	
	@Column(name="updated_at")


	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")
	private AppUser creationAppUser;

}
