package com.kevinpina.hibernatejpa;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientNewEntity;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class ClientNewInsert {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Type name: ");
            String name = scanner.next();
            System.out.println("Type surname: ");
            String surname = scanner.next();
            System.out.println("Type payment type: ");
            String paymentType = scanner.next();

            ClientNewEntity clientNewEntity = new ClientNewEntity();
            clientNewEntity.setName(name);
            clientNewEntity.setSurname(surname);
            clientNewEntity.setPaymentType(paymentType);

            em.getTransaction().begin();

            em.persist(clientNewEntity);

            em.getTransaction().commit();

            System.out.println(clientNewEntity); // After persist() will set the id attribute
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
