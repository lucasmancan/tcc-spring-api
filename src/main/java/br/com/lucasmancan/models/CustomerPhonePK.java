package br.com.lucasmancan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPhonePK implements Serializable {

	@Column( name ="client_id")
	private Long clientId;

	@Column( name ="phone_id")
	private Long phoneId;
}
