package br.com.lucasmancan.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClientAddressPK implements Serializable{
	
	@Column( name ="client_id", nullable = false)
	private Long clientId;

	@Column( name ="address_id", nullable = false)
	private Long addressId;

}
