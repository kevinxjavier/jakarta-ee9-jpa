package com.kevinpina.hibernatejpa.association.onetoone;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.RoleEntity;
import com.kevinpina.hibernatejpa.repository.entities.TypeRole;
import jakarta.persistence.EntityManager;

public class OneToOneAssociationRoleClient {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try {
            em.getTransaction().begin();

            ClientEntity clientEntity = ClientEntity.builder()
                    .name("anna").surname("boron").paymentType("master")
                    .build();

            em.persist(clientEntity);

            RoleEntity roleEntity = RoleEntity.builder()
                    .typeRole(TypeRole.MANAGER).description("sysadmin")
                    .build();

            // Also works at this point
            //clientEntity.setRoleEntity(roleEntity);

            em.persist(roleEntity);
            // If we do not persited will throw!
            //org.hibernate.TransientPropertyValueException: object references an unsaved transient instance -
            // save the transient instance before flushing :
            // com.kevinpina.hibernatejpa.repository.entities.ClientEntity.roleEntity -> com.kevinpina.hibernatejpa.repository.entities.RoleEntity

            clientEntity.setRoleEntity(roleEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);
            System.out.println(roleEntity);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
