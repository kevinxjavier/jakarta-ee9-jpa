package com.kevinpina.hibernatejpa;

import java.util.List;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class ClientQuery {

	public static void main(String[] args) {
		EntityManager em = JpaUtil.getEntitymanagerfactory();

		// **************************************************
		// createQuery()

		print("1");

		List<Client> clients1 = em.createQuery("SELECT c FROM Client c", Client.class).getResultList(); // Class Client of Type @Entity
		clients1.forEach(System.out::println);

		print("2");

		Query query1 = em.createQuery("SELECT c FROM Client c WHERE c.paymentType = ?1");
		query1.setParameter(1, "visa");
		Client client1 = (Client) query1.getSingleResult();
		System.out.println(client1);

		print("3");

		Query query2 = em.createQuery("SELECT c FROM Client c WHERE c.paymentType = ?1");
		query2.setParameter(1, "visa");
		List<Client> clients2 = query2.getResultList();
		clients2.forEach(System.out::println);

		print("4");

		// Limit to no more than one record
		Query query3 = em.createQuery("SELECT c FROM Client c WHERE c.paymentType = ?1");
		query3.setParameter(1, "master");
		query3.setMaxResults(1); // Property used to avoid jakarta.persistence.NonUniqueResultException
//		List<Client> clients3 = query3.getResultList();
//		clients3.forEach(System.out::println);
		Client client2 = (Client) query3.getSingleResult();
		System.out.println(client2);

		print("5");

		// **************************************************
		// find()
		// With this method optimized the query because will use the same query
		// result in the cache memory or session. Unless the id value of the class
		// change.

		Long idClient = 2L;
		Client client3 = em.find(Client.class, idClient); // works only with the id of the class
		System.out.println(client3);

		idClient = 1L;
		Client client4 = em.find(Client.class, idClient); // works only with the id of the class
		System.out.println(client4);

		em.close();
	}

	private static void print(String str) {
		System.out.println("\n" + str + " *********************************");
	}

}
