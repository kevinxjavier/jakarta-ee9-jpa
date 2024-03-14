package com.kevinpina.hibernatejpa;

import java.util.Scanner;

import javax.swing.JOptionPane;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;

import jakarta.persistence.EntityManager;

public class ClientUpdate {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(("Type the client id: "));
            Long idClient = scanner.nextLong();

            ClientEntity clientEntity = em.find(ClientEntity.class, idClient);

            String name = JOptionPane.showInputDialog("Type the name: ", clientEntity.getName());
            String surname = JOptionPane.showInputDialog("Type the surname: ", clientEntity.getSurname());
            String paymentType = JOptionPane.showInputDialog("Type the payment type: ", clientEntity.getPaymentType());

            clientEntity.setName(name);
            clientEntity.setSurname(surname);
            clientEntity.setPaymentType(paymentType);

            em.getTransaction().begin();

            em.merge(clientEntity); // If was not change any value will not update the DateTime 'modified' field

            em.getTransaction().commit();

            System.out.println(clientEntity);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

}
