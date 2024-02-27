package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor	// Entity must have always a Default Constructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "client")
public class ClientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String surname;

	@Column(name = "payment_type")
	private String paymentType;

	public ClientEntity(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

}