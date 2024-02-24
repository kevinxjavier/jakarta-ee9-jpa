package com.kevinpina.hibernatejpa.repositories;

import java.util.List;

import com.kevinpina.hibernatejpa.repository.entities.Client;

import jakarta.persistence.EntityManager;

public class ClientRepository implements CrudRepository<Client> {

	private EntityManager em;

	public ClientRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Client> list() {
		return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
	}

	@Override
	public Client findById(Long id) {
		return em.find(Client.class, id);
	}

	@Override
	public void save(Client client) {
		if (client.getId() != null && client.getId() > 0) {
			em.merge(client);
		}
		em.persist(client);
	}

	@Override
	public void delete(Long id) {
		Client client = findById(id);
		em.remove(client);
	}

}
