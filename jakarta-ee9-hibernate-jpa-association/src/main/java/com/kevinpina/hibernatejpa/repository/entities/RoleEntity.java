package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeRole typeRole;

    private String description;

    // Could be in ClientEntity which is the case or here, the class that has the @OneToOne as attribute contains the foreign key
    // in this case ClientEntity has the foreign key Client.role_id; so to get the client details we have to look the ClientEntity.role_id.
    // See the other @OneToOne example DetailEntity and DocumentEntity.
    //@OneToOne
    //private ClientEntity clientEntity;

}
