package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
//@NoArgsConstructor	// Entity must have always a Default Constructor
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

	// CascadeType.ALL: Everytime we create a Client will persist an Address in cascade "We can also use CascadeType.PERSIST"
	// orphanRemoval: By Default is false so if we remove an Address from Client removes it from intermediate table client_address but not from Address, so it will be there orphan in Address.
	// 		If orphanRemoval is true, and we remove a Client will remove the Addresses or if we remove an Address from the Client will remove it also from the intermediate table client_address.
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) // One=.class To Many=attribute (One Client has Many Addresses ). Using Cascade means that this class is the principal in the relationship.

	//@JoinColumn(name = "id_client") // if uncomment this line instead of create a table as the comment lines 47 or 53 below will create an id_client foreing key in Address table as address.id_client. @JoinTable must be commented.
	// alter table address add constraint FKh3o13l4287gb6ok1fm7tdo55r foreign key (id_client) references client (id)

	// Will create the same as lines 54 but personalized @JoinColumn must be commented.
	// joinColumns: main foreign key, will be id_client.
	// inverseJoinColumns: secondary foreign key, will be id_address.
	// uniqueConstraints: defines the columns that have constraints. In this case only id_address.
	@JoinTable(name = "tbl_client_address", joinColumns = @JoinColumn(name = "id_client"), inverseJoinColumns = @JoinColumn(name = "id_address"), uniqueConstraints = @UniqueConstraint(columnNames = {"id_address"})) // @See StudentEntity.courses
	// create table tbl_client_address (id_client bigint not null, id_address bigint not null)
	// alter table tbl_client_address add constraint UKol0lw1ya8f0pb1frvfu4i8063 unique (id_address)
	// alter table tbl_client_address add constraint FK8ohcnfjkcnb8ao0r7jcd1rhtn foreign key (id_address) references address (id)
	// alter table tbl_client_address add constraint FKro05xjeehjts97o3obru8dulh foreign key (id_client) references client (id)

	private List<AddressEntity> addressEntities = new ArrayList<>(); // will create a table classTable_attributeTable i.e.: "client_address". @JoinColumn and @JoinTable must be commented
	// create table client_address (ClientEntity_id bigint not null, addressEntities_id bigint not null)
	// alter table client_address add constraint FKq4w06d9qq4ilpdcijrd4l8twk foreign key (addressEntities_id) references address (id)
	// alter table client_address add constraint FKf6egv0vokfjjaksnxpj1t9pvh foreign key (ClientEntity_id) references client (id)


	// This relationship is bidirectional because it is also in defined in InvoiceEntity as: @ManyToOne ClientEntity client,
	// and so the owner of the relationship is InvoiceEntity, so it has want it or not the @JoinColumn.
	// mappedBy: for a relationship bidirectional, we have to indicate the inverse relationship with this property "mappedBy",
	// 		putting the attribute defined in InvoiceEntity that point to this class that is in this case InvoiceEntity.clientEntity.
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clientEntity") // mappedBy says that this class is the main and can't be with @JoinColumn. So @JoinColumn never goes with the mappedBy. 
	// Using Cascade means that this class is the principal in the relationship like the mappedBy.
	private List<InvoiceEntity> invoiceEntities;

	// Could be in RoleEntity or here which is the case, the class that has the @OneToOne as attribute contains the foreign key
	// in this case ClientEntity has the foreign key role_id; so to get the client details we have to look the ClientEntity.role_id.
	// See the other @OneToOne example DetailEntity and DocumentEntity.
	@OneToOne
	@JoinColumn(name = "role_id") // Changing the name of the default foreign key name Client.roleEntity_id
	private RoleEntity roleEntity;

	// This relationship is bidirectional because it is also in defined in ClientEntity as: @OneToOne ClientEntity clientEntity.
	// See the other @OneToOne example DetailEntity and RoleEntity.
	// CascadeType.ALL: Everytime we create a Client will persist a Document in cascade "We can also use CascadeType.PERSIST"
	// orphanRemoval: By Default is false so if we remove a Client will not remove it from Document, so it will be a client_id orphan in Document.
	// 		If orphanRemoval is true, and we remove a Client will remove the Document with the client_id from Document.
	// mappedBy: for a relationship bidirectional, we have to indicate the inverse relationship with this property "mappedBy",
	// 		putting the attribute defined in DocumentEntity that point to this class that is in this case DocumentEntity.clientEntity.
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clientEntity") // mappedBy says that this class is the main and can't be with @JoinColumn.
	// @JoinColumn(name = "document_id") // @JoinColumn defines which is the main class or the owner relationship or has the relationship. And never goes with the mappedBy.
	private DocumentEntity documentEntity;

	public ClientEntity() {
		// addressEntities = new ArrayList<>(); Initializing attribute in the constructor
		invoiceEntities = new ArrayList<>();
	}

	public ClientEntity(String name, String surname) {
		this(); // Calling default constructor to initialize invoiceEntities and addressEntities attributes
		this.name = name;
		this.surname = surname;
	}

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