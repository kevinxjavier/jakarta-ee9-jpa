package com.kevinpina.hibernatejpa.fetch_criteria;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import com.kevinpina.hibernatejpa.repository.entities.InvoiceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.List;

public class FetchManyToOneCriteria {

    // Optimizing the queries JPQL with "LEFT JOIN".

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<InvoiceEntity> query = builder.createQuery(InvoiceEntity.class);
        Root<InvoiceEntity> invoices = query.from(InvoiceEntity.class);

        // This clientEntity is from InvoiceEntity.clientEntity
        // JOIN from InvoiceEntity to ClientEntity
        // Fetch<InvoiceEntity, ClientEntity> clients = invoices.fetch("clientEntity", JoinType.LEFT); // By default is INNER JOIN.
        Join<InvoiceEntity, ClientEntity> clients = (Join) invoices.fetch("clientEntity", JoinType.LEFT); // By default is INNER JOIN.

        // This roleEntity is from ClientEntity.roleEntity
        // JOIN from ClientEntity to RoleEntity
        clients.fetch("roleEntity", JoinType.LEFT);

        query.select(invoices).where(builder.equal(clients.get("id"), 1L)); // clients is Cast to Join<> to use it with Where clause.

        List<InvoiceEntity> invoiceEntityList = em.createQuery(query).getResultList();

        System.out.println(invoiceEntityList);
        invoiceEntityList.forEach(System.out::println);

        // Even though it doesn't print in System.out.println the client info! Will fetch it with a SELECT unless we put FecthType.LAZY in:
        // class InvoiceEntity { @ManyToOne(fetch = FetchType.LAZY) private ClientEntity clientEntity; }
        // Now that we have FetchType.LAZY only will fetch it if we invoke directly with invoice.getClientEntity() like this case:
        invoiceEntityList.forEach( invoice -> System.out.println(invoice.getClientEntity().getName()));

        em.close();
    }

}
