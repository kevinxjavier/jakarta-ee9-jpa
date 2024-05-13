package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor  // Entity must have always a Default Constructor
@AllArgsConstructor
//@ToString         // Commenting to avoid recursive call in ClientEntity.toString.InvoiceEntity
@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    // This relationship is bidirectional because it is also in defined in ClientEntity as: @OneToMany List<InvoiceEntity> list,
    // If in ClientEntity exists a @OneToMany InvoiceEntity field at the same time with this attribute will be a Bidirectional relationship
    @ManyToOne(fetch = FetchType.LAZY) // Many=.class To One=attribute (Many Invoices has One Client)
    @JoinColumn(name = "id_client") // instead of create a default clientEntity_id foreing key name will create it with the name id_client
    private ClientEntity clientEntity; // will create a foreing key field in the table invoice "<table_attribute_name>_id" i.e.: clientEntity_id
    // alter table invoice add constraint FK6x8tbk56abpgu81wudr30bo3t foreign key (clientEntity_id) references client (id);

    // "id" is generated and "clientEntity" is a foreing key relation
    public InvoiceEntity(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getTotal(), that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getTotal());
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", total=" + total +
                '}';
    }

}
