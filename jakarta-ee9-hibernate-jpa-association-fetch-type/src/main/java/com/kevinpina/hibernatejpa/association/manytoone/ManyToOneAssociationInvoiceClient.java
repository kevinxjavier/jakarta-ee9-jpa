package com.kevinpina.hibernatejpa.association.manytoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.InvoiceEntity;
import jakarta.persistence.EntityManager;

public class ManyToOneAssociationInvoiceClient {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        // We can comment the @OneToMany List<InvoiceEntity> list in ClientEntity and write it again for the: OneToManyAssociationInvoiceClientBidirectional.
        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = ClientEntity.builder()
                    .name("kevin").surname("piña")
                    .paymentType("paypal").build();
            em.persist(clientEntity);

            System.out.println(clientEntity);

            InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                    .description("Office packages").total(1000L)
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
