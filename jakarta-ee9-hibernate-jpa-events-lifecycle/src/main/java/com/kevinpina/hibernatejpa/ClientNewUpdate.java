package com.kevinpina.hibernatejpa;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.Audit;
import com.kevinpina.hibernatejpa.repository.entities.ClientNewEntity;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.util.Scanner;

public class ClientNewUpdate {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(("Type the client id: "));
            Long idClient = scanner.nextLong();

            ClientNewEntity clientNewEntity = em.find(ClientNewEntity.class, idClient);
            // This validation is for when modified and created column are null both
            if (clientNewEntity.getAudit() == null) {
                clientNewEntity.setAudit(new Audit());
            }

            String name = JOptionPane.showInputDialog("Type the name: ", clientNewEntity.getName());
            String surname = JOptionPane.showInputDialog("Type the surname: ", clientNewEntity.getSurname());
            String paymentType = JOptionPane.showInputDialog("Type the payment type: ", clientNewEntity.getPaymentType());

            clientNewEntity.setName(name);
            clientNewEntity.setSurname(surname);
            clientNewEntity.setPaymentType(paymentType);

            em.getTransaction().begin();

            em.merge(clientNewEntity); // If was not change any value will not update the DateTime 'modified' field

            em.getTransaction().commit();

            System.out.println(clientNewEntity);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
