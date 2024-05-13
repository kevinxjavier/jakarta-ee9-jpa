package com.kevinpina.hibernatejpa.association.onetoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.DetailEntity;
import jakarta.persistence.EntityManager;

public class OneToOneAssociationDetailClient {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = ClientEntity.builder()
                    .name("anna").surname("boron").paymentType("master")
                    .build();

            em.persist(clientEntity);

            DetailEntity detailEntity = DetailEntity.builder()
                    .prime(true).totalPoints(3000L).clientEntity(clientEntity)
                    .build();

            em.persist(detailEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);
            System.out.println(detailEntity);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
