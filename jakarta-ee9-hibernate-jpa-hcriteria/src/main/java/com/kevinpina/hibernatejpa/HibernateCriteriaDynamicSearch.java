package com.kevinpina.hibernatejpa;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateCriteriaDynamicSearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Filter by name: ");
        String name = scanner.nextLine();

        System.out.println("Filter by surname: ");
        String surname = scanner.nextLine();

        System.out.println("Filter by payment type: ");
        String paymentType = scanner.nextLine();

        EntityManager em = JpaUtil.getEntitymanagerfactory();

        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> query = criteria.createQuery(ClientEntity.class);
        Root<ClientEntity> from = query.from(ClientEntity.class);

        List<Predicate> conditions = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            conditions.add(criteria.equal(from.get("name"), name));
        }
        if (surname != null && !surname.isEmpty()) {
            conditions.add(criteria.equal(from.get("surname"), surname));
        }
        if (paymentType != null && !paymentType.isEmpty()) {
            conditions.add(criteria.equal(from.get("paymentType"), paymentType));
        }

        query.select(from).where(criteria.and(conditions.toArray(new Predicate[conditions.size()])));

        List<ClientEntity> clients = em.createQuery(query).getResultList();
        clients.forEach(System.out::println);

        em.close();
    }

}
