package com.kevinpina.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JPAUtil {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();

	private static EntityManagerFactory buildEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("mysqlConnecion"); // Uses JPA configure in src/main/resources/META-INF/persistence.xml
	}

	public static EntityManager getEntityManager() {
		return ENTITY_MANAGER_FACTORY.createEntityManager();
	}

}