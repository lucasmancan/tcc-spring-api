package br.com.lucasmancan.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerAddressPK implements Serializable {
	
	@Column( name ="client_id", nullable = false)
	private Long clientId;

	@Column( name ="address_id", nullable = false)
	private Long addressId;

}
