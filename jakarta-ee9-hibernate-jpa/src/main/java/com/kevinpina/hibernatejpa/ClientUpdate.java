package com.kevinpina.hibernatejpa;

import java.util.Scanner;

import javax.swing.JOptionPane;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.Client;

import jakarta.persistence.EntityManager;

public class ClientUpdate {

	public static void main(String[] args) {

		EntityManager em = JpaUtil.getEntitymanagerfactory();

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println(("Type the client id: "));
			Long idClient = scanner.nextLong();

			Client client = em.find(Client.class, idClient);

			String name = JOptionPane.showInputDialog("Type the name: ", client.getName());
			String surname = JOptionPane.showInputDialog("Type the surname: ", client.getSurname());
			String paymentType = JOptionPane.showInputDialog("Type the payment type: ", client.getPaymentType());

			client.setName(name);
			client.setSurname(surname);
			client.setPaymentType(paymentType);
			
			em.getTransaction().begin();

			em.merge(client);

			em.getTransaction().commit();
			
			System.out.println(client);
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

}
