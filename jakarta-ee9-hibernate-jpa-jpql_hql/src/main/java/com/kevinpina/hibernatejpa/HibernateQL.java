package com.kevinpina.hibernatejpa;

import com.kevinpina.hibernatejpa.domain.dto.ClientDTO;
import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;
import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

public class HibernateQL {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        // HQL queries using class name ClientEntity insted of table client

        List<ClientEntity> client0 = em.createQuery("SELECT c FROM ClientEntity c", ClientEntity.class).getResultList();
        ClientEntity client1 = em.find(ClientEntity.class, 1L);
        ClientEntity client2 = em.createQuery("SELECT c FROM ClientEntity c WHERE c.id = ?1", ClientEntity.class)
                .setParameter(1, 1L)
                .getSingleResult();
        ClientEntity client3 = em.createQuery("SELECT c FROM ClientEntity c WHERE c.id = :clientId", ClientEntity.class)
                .setParameter("clientId", 1L)
                .getSingleResult();
        String clientName = em.createQuery("SELECT c.name FROM ClientEntity c WHERE c.id = 1", String.class)
                .getSingleResult();
        List<String> clientNames = em.createQuery("SELECT c.name FROM ClientEntity c", String.class)
                .getResultList();
        Object[] client4 = em.createQuery("SELECT c.id, c.name, c.surname FROM ClientEntity c WHERE c.id = :id", Object[].class)
                .setParameter("id", 1L)
                .getSingleResult();
        List<Object[]> client5 = em.createQuery("SELECT c.id, c.name, c.surname FROM ClientEntity c", Object[].class)
                .getResultList();
        List<Object[]> client6 = em.createQuery("SELECT c, c.paymentType FROM ClientEntity c", Object[].class)
                .getResultList();
        List<ClientEntity> client7 = em.createQuery("SELECT new ClientEntity(c.name, c.surname) FROM ClientEntity c", ClientEntity.class)
                .getResultList();
        List<ClientDTO> client8 = em.createQuery("SELECT new com.kevinpina.hibernatejpa.domain.dto.ClientDTO(c.name, c.surname) FROM ClientEntity c", ClientDTO.class)
                .getResultList();
        List<String> clientPaymentTypes = em.createQuery("SELECT DISTINCT(c.paymentType) FROM ClientEntity c", String.class).getResultList();
        Long clientPaymentTypesCount = em.createQuery("SELECT COUNT(DISTINCT(c.paymentType)) FROM ClientEntity c", Long.class).getSingleResult();
        Long clientMin = em.createQuery("SELECT MIN(c.id) AS minimum FROM ClientEntity c", Long.class).getSingleResult();
        Long clientMax = em.createQuery("SELECT MAX(c.id) AS maximum FROM ClientEntity c", Long.class).getSingleResult();
        //List<String> nameAndSurname = em.createQuery("SELECT CONCAT(c.name, ' ', c.surname) AS fullname FROM ClientEntity c", String.class).getResultList();
        List<String> nameAndSurname = em.createQuery("SELECT c.name || ' ' || c.surname AS fullname FROM ClientEntity c", String.class).getResultList();
        List<String> nameAndSurnameUpperCase = em.createQuery("SELECT UPPER(CONCAT(c.name, ' ', c.surname)) AS fullname FROM ClientEntity c", String.class).getResultList();
        List<String> nameAndSurnameLowerCase = em.createQuery("SELECT LOWER(CONCAT(c.name, ' ', c.surname)) AS fullname FROM ClientEntity c", String.class).getResultList();
        List<ClientEntity> clientsFound = em.createQuery("SELECT c FROM ClientEntity c WHERE UPPER(c.surname) LIKE UPPER(:surname)", ClientEntity.class)
                .setParameter("surname", "%ron%")
                .getResultList();
        List<ClientEntity> clientsBetweenId = em.createQuery("SELECT c FROM ClientEntity c WHERE c.id BETWEEN 2 AND 5", ClientEntity.class).getResultList();
        List<ClientEntity> clientsBetweenName = em.createQuery("SELECT c FROM ClientEntity c WHERE c.name BETWEEN 'J' AND 'M'", ClientEntity.class).getResultList(); // Not include 'M'
        //List<ClientEntity> clientsIN = em.createQuery("SELECT c FROM ClientEntity c WHERE c.id IN (1, 3, 5)", ClientEntity.class).getResultList();
        List<ClientEntity> clientsIN = em.createQuery("SELECT c FROM ClientEntity c WHERE c.id IN :ids", ClientEntity.class)
                .setParameter("ids", Arrays.asList(1L, 3L, 5L))
                .getResultList();
        List<ClientEntity> clientsOrderBy = em.createQuery("SELECT c FROM ClientEntity c ORDER BY c.name ASC, c.surname ASC", ClientEntity.class).getResultList();
        List<Object[]> clientsNameAndLength = em.createQuery("SELECT c.name, LENGTH(c.name) FROM ClientEntity c", Object[].class).getResultList();
        Integer minClientName = em.createQuery("SELECT MIN(LENGTH(c.name)) FROM ClientEntity c", Integer.class).getSingleResult();
        Integer maxClientName = em.createQuery("SELECT MAX(LENGTH(c.name)) FROM ClientEntity c", Integer.class).getSingleResult();
        Object[] statistic = em.createQuery("SELECT MIN(c.id), MAX(c.id), SUM(c.id), COUNT(c.id), AVG(LENGTH(c.name)) FROM ClientEntity c", Object[].class).getSingleResult(); // Aggregate Functions
        List<Object[]> clientMinName = em.createQuery("SELECT c.name, LENGTH(c.name) FROM ClientEntity c WHERE LENGTH(c.name) = (SELECT MIN(LENGTH(c.name)) FROM ClientEntity c)", Object[].class).getResultList();
        ClientEntity clientLast = em.createQuery("SELECT c FROM ClientEntity c WHERE c.id = (SELECT MAX(c.id) FROM ClientEntity c)", ClientEntity.class).getSingleResult();

        System.out.println("------------------------------ List<ClientEntity> client0 ------------------------------");
        client0.forEach(System.out::println);
        System.out.println("------------------------------ ClientEntity client1 ------------------------------");
        System.out.println(client1);
        System.out.println("------------------------------ ClientEntity client2 ------------------------------");
        System.out.println(client2);
        System.out.println("------------------------------ ClientEntity client3 ------------------------------");
        System.out.println(client3);
        System.out.println("------------------------------ String clientName ------------------------------");
        System.out.println(clientName);
        System.out.println("------------------------------ List<String> clientNames ------------------------------");
        System.out.println(clientNames);
        System.out.println("------------------------------ Object[] client4 ------------------------------");
        System.out.println(client4[0] + " " + client4[1] + " " + client4[2]);
        System.out.println("------------------------------ List<Object[]> client5 ------------------------------");
        client5.forEach(c -> System.out.println((Long)c[0] + " " + (String)c[1] + " " + (String)c[2]));
        System.out.println("------------------------------ List<Object[]> client6 ------------------------------");
        client6.forEach(client -> {
            ClientEntity clientEntity = (ClientEntity) client[0];
            String paymentType = (String) client[1];
            System.out.println("paymentType = [" + paymentType + "], client = [" + clientEntity + "]");
        });
        System.out.println("------------------------------ List<ClientEntity> client7 ------------------------------");
        client7.forEach(System.out::println);
        System.out.println("------------------------------ List<ClientDTO> client8 ------------------------------");
        client8.forEach(System.out::println);
        System.out.println("------------------------------ List<String> clientPaymentTypes DISTINCT() ------------------------------");
        System.out.println(clientPaymentTypes);
        System.out.println("------------------------------ Long clientPaymentTypesCount COUNT(DISTINCT()) ------------------------------");
        System.out.println("clientPaymentTypesCount = [" + clientPaymentTypesCount + "]");
        System.out.println("------------------------------ Long clientMin MIN() ------------------------------");
        System.out.println("clientMin = [" + clientMin + "]");
        System.out.println("------------------------------ Long clientMax MAX() ------------------------------");
        System.out.println("clientMax = [" + clientMax + "]");
        System.out.println("------------------------------ List<String> nameAndSurname CONCAT() or || ------------------------------");
        nameAndSurname.forEach(System.out::println);
        System.out.println("------------------------------ List<String> nameAndSurnameLowerCase UPPER() ------------------------------");
        nameAndSurnameUpperCase.forEach(System.out::println);
        System.out.println("------------------------------ List<String> nameAndSurnameLowerCase LOWER() ------------------------------");
        nameAndSurnameLowerCase.forEach(System.out::println);
        System.out.println("------------------------------ List<ClientEntity> clientFound LIKE ------------------------------");
        clientsFound.forEach(System.out::println);
        System.out.println("------------------------------ List<ClientEntity> clientsBetweenId BETWEEN ------------------------------");
        clientsBetweenId.forEach(System.out::println);
        System.out.println("------------------------------ List<ClientEntity> clientsBetweenName BETWEEN ------------------------------");
        clientsBetweenName.forEach(System.out::println);
        System.out.println("------------------------------ List<ClientEntity> clientsIN IN ------------------------------");
        clientsIN.forEach(System.out::println);
        System.out.println("------------------------------ List<ClientEntity> clientsOrderBy ORDER BY ------------------------------");
        clientsOrderBy.forEach(System.out::println);
        System.out.println("------------------------------ List<Object[]> clientsNameAndLength LENGTH() ------------------------------");
        clientsNameAndLength.forEach(o -> System.out.println("name = " + (String)o[0] + ", length = " + (Integer)o[1]));
        System.out.println("------------------------------ Integer minClientName MIN(LENGTH()) ------------------------------");
        System.out.println("minClientName = " + minClientName);
        System.out.println("------------------------------ Integer maxClientName MAX(LENGTH()) ------------------------------");
        System.out.println("maxClientName = " + maxClientName);
        System.out.println("------------------------------ Object[] statistic MIN() MAX() SUN() COUNT() AVG() LENGTH() ------------------------------");
        System.out.println("min = " + (Long)statistic[0] + ", max = " + (Long)statistic[1] + ", sum = " + (Long)statistic[2] + ", count = " + (Long)statistic[3] + ", avg = " + (Double)statistic[4]);
        System.out.println("------------------------------ Sub-Queries ------------------------------");
        clientMinName.forEach(c -> System.out.println("Minimum length: name = " + c[0] + ", size = " + c[1]));
        System.out.println("Last client registered = " + clientLast);

        em.close();
    }

}