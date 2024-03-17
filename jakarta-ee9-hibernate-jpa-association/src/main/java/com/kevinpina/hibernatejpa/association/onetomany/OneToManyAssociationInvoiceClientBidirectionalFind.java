package com.kevinpina.hibernatejpa.association.onetomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.InvoiceEntity;
import jakarta.persistence.EntityManager;

public class OneToManyAssociationInvoiceClientBidirectionalFind {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        OneToManyAssociationInvoiceClientBidirectional.main(new String[]{});

        try {
            ClientEntity clientEntity = em.find(ClientEntity.class, 1L);
            clientEntity.setPaymentType("bitcoin");

            InvoiceEntity invoiceEntity1 = InvoiceEntity.builder()
                    .clientEntity(clientEntity).description("car").total(19000L).build();
            InvoiceEntity invoiceEntity2 = InvoiceEntity.builder()
                    .clientEntity(clientEntity).description("moto").total(1150L).build();

            clientEntity.getInvoiceEntities().add(invoiceEntity1);
            clientEntity.getInvoiceEntities().add(invoiceEntity2);

            em.getTransaction().begin();
            // em.merge(clientEntity); // this optional, because once we made em.find() it is already in the persistence context.
            em.getTransaction().commit();

            System.out.println(clientEntity);


            // Delete clientEntity.getInvoiceEntities().get(0) "laptop"
            em.getTransaction().begin();

            //InvoiceEntity invoiceEntity = em.find(InvoiceEntity.class, 1L);
            InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                    .id(1L).description("laptop").total(2500L).build(); // InvoiceEntity must implements equals() in order that work with List.remove()

            clientEntity.getInvoiceEntities().remove(invoiceEntity);
            invoiceEntity.setClientEntity(null);

            // em.merge(clientEntity); // this optional, because once we made em.find() it is already in the persistence context.
            em.getTransaction().commit();

            System.out.println(clientEntity);

        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
