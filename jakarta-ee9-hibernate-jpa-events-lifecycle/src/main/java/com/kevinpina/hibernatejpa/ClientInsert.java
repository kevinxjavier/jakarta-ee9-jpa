package com.kevinpina.hibernatejpa;
import java.util.Scanner;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;

import jakarta.persistence.EntityManager;

public class ClientInsert {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println("Type name: ");
            String name = scanner.next();
            System.out.println("Type surname: ");
            String surname = scanner.next();
            System.out.println("Type payment type: ");
            String paymentType = scanner.next();

            ClientEntity clientEntity = ClientEntity.builder().name(name).surname(surname).paymentType(paymentType).build();

            em.getTransaction().begin();

            em.persist(clientEntity);

            System.out.println(clientEntity); // After persist() will set the id attribute

            ClientEntity clientEntityInserted = em.find(ClientEntity.class, clientEntity.getId()); // Won't execute a select 'cause exists in JPA Context
            System.out.println(clientEntityInserted);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

}