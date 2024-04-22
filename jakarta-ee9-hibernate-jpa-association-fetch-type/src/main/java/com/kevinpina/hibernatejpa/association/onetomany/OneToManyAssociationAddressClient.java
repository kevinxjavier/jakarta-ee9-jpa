package com.kevinpina.hibernatejpa.association.onetomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.AddressEntity;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class OneToManyAssociationAddressClient {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try{
            em.getTransaction().begin();

            ClientEntity clientEntity = ClientEntity.builder().name("javier").surname("calatrava").paymentType("visa").build();

            AddressEntity address1 = AddressEntity.builder().number(29).streetName("alonso núñez").build();
            AddressEntity address2 = AddressEntity.builder().number(120).streetName("carmen").build();
            // List<AddressEntity> addressEntities = Arrays.asList(address1, address2); // java.lang.UnsupportedOperationException: when try to remove an element from the array
            List<AddressEntity> addressEntities = new ArrayList<>();
            addressEntities.add(address1);
            addressEntities.add(address2);

            clientEntity.setAddressEntities(addressEntities);
            // clientEntity.getAddressEntities().add(address1);
            // clientEntity.getAddressEntities().add(address2);

            em.persist(clientEntity);

            em.getTransaction().commit();

            System.out.println(clientEntity);


            // Delete address1
            em.getTransaction().begin();

            ClientEntity client = em.find(ClientEntity.class, clientEntity.getId());

            client.getAddressEntities().remove(address1);
            // client is attached in the context because of persist(), whatever change will do it after commit will be executed!
            // em.getTransaction().merge(client); so this is unnecessary
            em.getTransaction().commit();

            System.out.println(client);

        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
