package com.kevinpina.hibernatejpa.fetch;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FetchLazyOneToManyJoinFetchResultList {

    // Optimizing the queries JPQL with "LEFT JOIN".

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        // LEFT JOIN = LEFT OUTER JOIN
        // FETCH to populate the data in ClientEntity if we don't put it will appear the columns but without data

        //String sql = "SELECT c FROM ClientEntity c"; // We use the query below to optimize it with the LEFT JOIN
        String sql = "SELECT DISTINCT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity";
        List<ClientEntity> clientEntities = em.createQuery(sql, ClientEntity.class)
                .getResultList();

        /*
            NOTE 1: We cannot use  @OneToMany(fetch = FetchType.EAGER) twice in the same class because it will throw: cannot simultaneously fetch multiple bag [...invoiceEntities, ...addressEntities]

                class ClientEntity {
                    @OneToMany()
                    List<InvoiceEntity> invoiceEntities;

                    @OneToMany()
                    List<AddressEntity> addressEntities;
                }

            NOTE 2: In a query "SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";
                we cannot use "SELECT ... LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.invoiceEntities ...;" because it will throw: cannot simultaneously fetch multiple bag [...invoiceEntities, ...addressEntities]
                because both are @OneToMany.

                Recommendation:
                    A) For that case it is better have 2 queries:
                        List<ClientEntity> clientAddressesEntities = em.createQuery("SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";, ClientEntity.class).getResultList();
                        List<ClientEntity> clientInvoicesEntities = em.createQuery("SELECT c FROM ClientEntity c LEFT JOIN FETCH c.invoiceEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id";, ClientEntity.class).getResultList();

                    B) Or if it is a small database but still not recommended, change one of the @OneToMany for Set<> instead if List<>.
                        List<ClientEntity> clientAddressesInvoicesEntities = em.createQuery("SELECT c FROM ClientEntity c LEFT JOIN FETCH c.addressEntities LEFT JOIN FETCH c.invoiceEntities LEFT JOIN FETCH c.documentEntity WHERE c.id = :id", ClientEntity.class).getResultList();

                        class ClientEntity {
                            @OneToMany()
                            List<InvoiceEntity> invoiceEntities;

                            @OneToMany()
                            Set<AddressEntity> addressEntities;
                        }

        */

        // If we don't use DISTINCT will repeat the client id 1 twice because it has 2 addresses
        // ClientEntity(id=1, name=kevin, surname=pi?a, paymentType=visa, createdTime=null, modifiedTime=null, addressEntities=[AddressEntity(id=1, streetName=Lomas del Avila, Loma Suite 2, A-14, number=2), AddressEntity(id=2, streetName=Carmen 120, 703, number=120)], invoiceEntities=[], roleEntity=null, documentEntity=DocumentEntity{id=1, documentTypeEnum=RUT})
        // ClientEntity(id=1, name=kevin, surname=pi?a, paymentType=visa, createdTime=null, modifiedTime=null, addressEntities=[AddressEntity(id=1, streetName=Lomas del Avila, Loma Suite 2, A-14, number=2), AddressEntity(id=2, streetName=Carmen 120, 703, number=120)], invoiceEntities=[], roleEntity=null, documentEntity=DocumentEntity{id=1, documentTypeEnum=RUT})
        // ClientEntity(id=2, name=javier, surname=calatrava, paymentType=master, createdTime=null, modifiedTime=null, addressEntities=[AddressEntity(id=3, streetName=Alonso Núñez 29, 1C, number=29)], invoiceEntities=[], roleEntity=null, documentEntity=null)
        // ClientEntity(id=3, name=anna, surname=boron, paymentType=american express, createdTime=null, modifiedTime=null, addressEntities=[], invoiceEntities=[], roleEntity=null, documentEntity=null)
        // ...

        // If we call getAddressEntities() or getDocumentEntity() will execute another SELECTS to bring them when is FetchType.LAZY
        // otherwise if we put FetchType.EAGER will execute another select automatically to bring them.
        clientEntities.forEach(System.out::println);
        em.close();
    }

}
