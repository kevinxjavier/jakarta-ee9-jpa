package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

	@Column(name = "created")
	private LocalDateTime createdTime;

	@Column(name = "modified")
	private LocalDateTime modifiedTime;

	@PrePersist
	public void prePersist() {
		System.out.println("Init something before persist");
		createdTime = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		System.out.println("Init something before update");
		modifiedTime = LocalDateTime.now();
	}

}