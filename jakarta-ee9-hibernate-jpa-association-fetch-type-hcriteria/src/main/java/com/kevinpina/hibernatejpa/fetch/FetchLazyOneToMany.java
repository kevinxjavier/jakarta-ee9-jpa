package com.kevinpina.hibernatejpa.fetch;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;

public class FetchLazyOneToMany {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        ClientEntity clientEntity = em.find(ClientEntity.class, 1L);

        // If we call getAddressEntities() or getDocumentEntity() will execute another SELECTS to bring them when is FetchType.LAZY
        // otherwise if we put FetchType.EAGER will execute another select automatically to bring them.
        // System.out.println(clientEntity.getAddressEntities());

        em.close();
        // Because we put FetchType.EAGER in ClientEntity ... @OneToMany(fetch = FetchType.EAGER, ...) List<AddressEntity> addressEntities; will not throw after em.close() the: could not initialize proxy - no Session
        System.out.println(clientEntity.getAddressEntities());
    }

}
