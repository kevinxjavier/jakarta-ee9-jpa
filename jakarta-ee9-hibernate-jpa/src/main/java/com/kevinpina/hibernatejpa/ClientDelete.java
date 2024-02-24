package com.kevinpina.hibernatejpa;

import java.util.Scanner;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.Client;

import jakarta.persistence.EntityManager;

public class ClientDelete {

	public static void main(String[] args) {

		EntityManager em = JpaUtil.getEntitymanagerfactory();

		try (Scanner scanner = new Scanner(System.in);) {
			System.out.println("Type client id: ");
			Long idClient = scanner.nextLong();

			Client client = em.find(Client.class, idClient);

			em.getTransaction().begin();
			em.remove(client); // Won't work with new Client() must be initialize with JPA Context
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

}
