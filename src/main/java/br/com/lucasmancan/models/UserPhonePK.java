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
public class UserPhonePK implements Serializable{

	@Column( name ="user_id")
	private Long userId;

	@Column( name ="phone_id")
	private Long phoneId;
}
