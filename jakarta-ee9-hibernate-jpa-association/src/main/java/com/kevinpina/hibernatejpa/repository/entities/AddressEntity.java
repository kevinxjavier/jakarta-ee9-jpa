package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor    // Entity must have always a Default Constructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetName;
    private Integer number;

    public AddressEntity(String streetName, Integer number) {
        this.streetName = streetName;
        this.number = number;
    }

}
