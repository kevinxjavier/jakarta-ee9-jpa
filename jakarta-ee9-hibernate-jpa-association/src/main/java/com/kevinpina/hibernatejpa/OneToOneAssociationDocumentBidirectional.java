package com.kevinpina.hibernatejpa;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.DocumentEntity;
import com.kevinpina.hibernatejpa.repository.entities.DocumentType;
import jakarta.persistence.EntityManager;

public class OneToOneAssociationDocumentBidirectional {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = ClientEntity.builder()
                    .name("Julian").surname("Assange").paymentType("paypal")
                    .build();

            em.persist(clientEntity);

            DocumentEntity documentEntity = DocumentEntity.builder().documentTypeEnum(DocumentType.PASSPORT).build();


            // Also works at this point
            //clientEntity.setDocumentEntity(documentEntity);

            em.persist(documentEntity);
            // If we do not persited will throw!
            //org.hibernate.TransientPropertyValueException: object references an unsaved transient instance -
            // save the transient instance before flushing :
            // com.kevinpina.hibernatejpa.repository.entities.ClientEntity.documentEntity -> com.kevinpina.hibernatejpa.repository.entities.DocumentEntity

            clientEntity.setDocumentEntity(documentEntity);

            em.getTransaction().commit();
        } catch (Exception e ) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
