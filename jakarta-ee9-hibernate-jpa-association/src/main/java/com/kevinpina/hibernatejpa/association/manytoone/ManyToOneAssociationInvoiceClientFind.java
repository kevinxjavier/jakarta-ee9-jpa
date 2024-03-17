package com.kevinpina.hibernatejpa.association.manytoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.InvoiceEntity;
import jakarta.persistence.EntityManager;

public class ManyToOneAssociationInvoiceClientFind {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        // We can delete the @OneToMany List<InvoiceEntity> list in ClientEntity and write it again for the: OneToManyAssociationInvoiceClientBidirectional.

        // If comment this line will persist the invoice with client null
        ManyToOneAssociationInvoiceClient.main(new String[]{});

        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = em.find(ClientEntity.class, 1L);

            System.out.println(clientEntity);

            InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                    .description("Computer packages").total(500L)
                    .clientEntity(clientEntity).build();
            em.persist(invoiceEntity);

            System.out.println(invoiceEntity);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

}
