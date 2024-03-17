package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString             // Commenting to avoid recursive call in ClientEntity.toString.DocumentEntity
@Entity
@Table(name = "document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type") // Calling the column document.type
    private DocumentType documentTypeEnum;

    // This relationship is bidirectional because it is also in defined in ClientEntity as: @OneToOne DocumentEntity documentEntity.
    @OneToOne
    @JoinColumn(name = "client_id") // Calling the name of the foreign key column document.client_id. If comment this line will be document.clientEntity_id
    private ClientEntity clientEntity;

    @Override
    public String toString() {
        return "DocumentEntity{" +
                "id=" + id +
                ", documentTypeEnum=" + documentTypeEnum +
                '}';
    }
}
