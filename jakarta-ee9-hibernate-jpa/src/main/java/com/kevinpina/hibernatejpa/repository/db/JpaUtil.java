package com.kevinpina.hibernatejpa.repository.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

	private static final EntityManagerFactory entityManagerFactory = builderEnttEntityManagerFactory();

	private static EntityManagerFactory builderEnttEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("conexionJPA");
	}

	// Always use the same instance because is Singleton
	public static EntityManager getEntitymanagerfactory() {
		return entityManagerFactory.createEntityManager();
	}

}
