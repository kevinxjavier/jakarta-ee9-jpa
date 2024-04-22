package com.kevinpina.hibernatejpa.association.onetoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.DocumentEntity;
import com.kevinpina.hibernatejpa.repository.entities.DocumentType;
import jakarta.persistence.EntityManager;

public class OneToOneAssociationDocumentClientBidirectionalFind {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        OneToOneAssociationDocumentClientBidirectional.main(new String[]{});

        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = em.find(ClientEntity.class, 10L);
            DocumentEntity documentEntity = DocumentEntity.builder().documentTypeEnum(DocumentType.RUT).build();

            clientEntity.setDocumentEntity(documentEntity);
            documentEntity.setClientEntity(clientEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);
            System.out.println(documentEntity);

            // Delete clientEntity
            em.getTransaction().begin();

            em.remove(clientEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

}
