package com.kevinpina.hibernatejpa.fetch_criteria;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class FetchOneToManyCriteria {

    // Optimizing the queries JPQL with "LEFT JOIN".

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> query = builder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot = query.from(ClientEntity.class);

        // This addressEntities is from ClientEntity.addressEntities
        // JOIN from ClientEntity to AddressEntity
        clientEntityRoot.fetch("addressEntities", JoinType.LEFT); // By default is INNER JOIN.

        // This documentEntity is from ClientEntity.documentEntity
        // JOIN from ClientEntity to DocumentEntity
        clientEntityRoot.fetch("documentEntity", JoinType.LEFT); // By default is INNER JOIN.

        query.select(clientEntityRoot).distinct(true); // we use DISTINCT because the JOIN and because we have an user with 2 addresses
        List<ClientEntity> clientEntityList = em.createQuery(query).getResultList();

        clientEntityList.forEach(c -> System.out.println(c.getName() + " " + c.getAddressEntities()));

        System.out.println(clientEntityList);

        em.close();
    }
}
