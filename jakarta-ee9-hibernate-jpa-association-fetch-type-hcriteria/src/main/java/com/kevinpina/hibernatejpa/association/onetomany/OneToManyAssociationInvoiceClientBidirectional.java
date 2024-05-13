package com.kevinpina.hibernatejpa.association.onetomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.InvoiceEntity;
import jakarta.persistence.EntityManager;

public class OneToManyAssociationInvoiceClientBidirectional {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try {
            ClientEntity clientEntity = new ClientEntity("kevin", "piña");
            clientEntity.setPaymentType("bitcoin");

            InvoiceEntity invoiceEntity1 = InvoiceEntity.builder()
                    .clientEntity(clientEntity).description("laptop").total(2500L).build();
            InvoiceEntity invoiceEntity2 = InvoiceEntity.builder()
                    .clientEntity(clientEntity).description("monitor").total(150L).build();

            clientEntity.getInvoiceEntities().add(invoiceEntity1);
            clientEntity.getInvoiceEntities().add(invoiceEntity2);

            em.getTransaction().begin();
            em.persist(clientEntity);
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
