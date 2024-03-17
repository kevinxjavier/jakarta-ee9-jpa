package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "detail")
public class DetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean prime;

    @Column(name = "total_points")
    private Long totalPoints;

    // Could be in ClientEntity or here which is the case, the class that has the @OneToOne as attribute contains the foreign key
    // in this case DetailEntity has the foreign key client_id; so to get the client details we have to look the DetailEntity.client_id.
    // See the other @OneToOne example ClientEntity, RoleEntity and DocumentEntity.
    @OneToOne
    //@JoinColumn(name = "client_id") // Changing the name of the default foreign key name Detail.clientEntity_id
    private ClientEntity clientEntity;

}
