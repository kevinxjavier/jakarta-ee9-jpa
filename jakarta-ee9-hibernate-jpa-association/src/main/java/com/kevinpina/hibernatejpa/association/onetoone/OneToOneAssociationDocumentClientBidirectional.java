package com.kevinpina.hibernatejpa.association.onetoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.DocumentEntity;
import com.kevinpina.hibernatejpa.repository.entities.DocumentType;
import jakarta.persistence.EntityManager;

public class OneToOneAssociationDocumentClientBidirectional {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try {
            em.getTransaction().begin();

            // Main class and handles the cascade.
            ClientEntity clientEntity = ClientEntity.builder()
                    .name("Julian").surname("Assange").paymentType("paypal")
                    .build();

            // If persist() at this point will be an Update, so Client.modified will have a value,
            // does not matter that 33 em.persist() line is also uncommented
            //em.persist(clientEntity);

            // Secondary but it is the owner of the relationship so has the @JoinColumn.
            DocumentEntity documentEntity = DocumentEntity.builder().documentTypeEnum(DocumentType.CI).build();

            clientEntity.setDocumentEntity(documentEntity);
            documentEntity.setClientEntity(clientEntity);

            // Just only Client.created will have a value.
            em.persist(clientEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);
            System.out.println(documentEntity);

        } catch (Exception e ) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
