package com.kevinpina.hibernatejpa.services;

import java.util.List;
import java.util.Optional;

import com.kevinpina.hibernatejpa.repositories.ClientRepository;
import com.kevinpina.hibernatejpa.repositories.CrudRepository;
import com.kevinpina.hibernatejpa.repository.entities.Client;

import jakarta.persistence.EntityManager;

public class ClientServiceImpl implements ClientService {

	private EntityManager em;
	private CrudRepository<Client> repository;

	public ClientServiceImpl(EntityManager em) {
		this.em = em;
		this.repository = new ClientRepository(em);
	}

	@Override
	public List<Client> list() {
		return repository.list();
	}

	@Override
	public Optional<Client> findById(Long id) {
		return Optional.ofNullable(repository.findById(id));
	}

	@Override
	public void save(Client client) {
		try {
			em.getTransaction().begin();
			repository.save(client);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		try {
			em.getTransaction().begin();
			repository.delete(id);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}

}
