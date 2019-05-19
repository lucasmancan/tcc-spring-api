package br.com.lucasmancan.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEmailPK implements Serializable{

	@Column( name ="client_id")
	private Long clientId;

	@Column( name ="email_id")
	private Long emailId;
}
