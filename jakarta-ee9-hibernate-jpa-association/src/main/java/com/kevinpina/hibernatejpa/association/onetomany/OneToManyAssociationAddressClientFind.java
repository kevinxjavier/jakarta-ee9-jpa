package com.kevinpina.hibernatejpa.association.onetomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.AddressEntity;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;

public class OneToManyAssociationAddressClientFind {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        OneToManyAssociationAddressClient.main(new String[]{});

        try{
            em.getTransaction().begin();

            ClientEntity clientEntity = em.find(ClientEntity.class, 1L);

            AddressEntity address1 = AddressEntity.builder().number(1).streetName("lomas del avila").build();
            clientEntity.getAddressEntities().add(address1);

            em.merge(clientEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);


            // Delete address1
            em.getTransaction().begin();

            // Address is not in the context because it wasn't persist() with client or address object. It was just a merge().
            // clientEntity.getAddressEntities().remove(address1); so if we execute this without find the Address first will no removes it.

            address1 = em.find(AddressEntity.class, 3L); // If this line is commented, Address will not be in the context, so it will not be removes, because address1.id = null.
            clientEntity.getAddressEntities().remove(address1);

            em.getTransaction().commit();

            System.out.println();

        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
