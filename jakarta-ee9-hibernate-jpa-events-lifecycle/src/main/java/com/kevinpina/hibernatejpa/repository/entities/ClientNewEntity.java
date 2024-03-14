package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor	// Entity must have always a Default Constructor
@ToString
@Entity
@Table(name = "client_new")
public class ClientNewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Column(name = "payment_type")
    private String paymentType;

    @Embedded
    private Audit audit = new Audit();

    /*
    @Override
    public String toString() {
        // Validate Audit instance due could be both columns createdTime and modifiedTime with null value
        // so Audit instace will be null
        LocalDateTime createdTime = null;
        LocalDateTime modifiedTime = null;
        if (audit != null) {
            createdTime = audit.getCreatedTime();
            modifiedTime = audit.getModifiedTime();
        }
        return "ClientNewEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", createdTime=" + createdTime +  '\'' +
                ", modifiedTime=" + modifiedTime +
                '}';
    }*/

}
