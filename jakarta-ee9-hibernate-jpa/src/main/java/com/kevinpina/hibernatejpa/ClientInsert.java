package com.kevinpina.hibernatejpa;

import java.util.Scanner;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.Client;

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

			Client client = Client.builder().name(name).surname(surname).paymentType(paymentType).build();
			
			em.getTransaction().begin();
			
			em.persist(client);
			
			System.out.println(client); // After persist() will set the id attribute
			
			Client clientInserted = em.find(Client.class, client.getId()); // Won't execute a select 'cause exists in JPA Context 
			System.out.println(clientInserted);

			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

}
