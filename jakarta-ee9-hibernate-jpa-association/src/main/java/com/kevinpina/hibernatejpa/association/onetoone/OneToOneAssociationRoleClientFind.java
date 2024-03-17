package com.kevinpina.hibernatejpa.association.onetoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.RoleEntity;
import com.kevinpina.hibernatejpa.repository.entities.TypeRole;
import jakarta.persistence.EntityManager;

public class OneToOneAssociationRoleClientFind {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        OneToOneAssociationRoleClient.main(new String[]{});

        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = em.find(ClientEntity.class, 1L);

            RoleEntity roleEntity = RoleEntity.builder()
                    .typeRole(TypeRole.USER).description("developer")
                    .build();

            em.persist(roleEntity);

            clientEntity.setRoleEntity(roleEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);
            System.out.println(roleEntity);

        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
             em.close();
        }
    }

}
