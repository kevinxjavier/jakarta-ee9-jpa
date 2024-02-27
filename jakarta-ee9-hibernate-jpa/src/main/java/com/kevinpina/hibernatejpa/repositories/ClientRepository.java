package com.kevinpina.hibernatejpa.repositories;

import java.util.List;

import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;

import jakarta.persistence.EntityManager;

public class ClientRepository implements CrudRepository<ClientEntity> {

	private EntityManager em;

	public ClientRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<ClientEntity> list() {
		return em.createQuery("SELECT c FROM ClientEntity c", ClientEntity.class).getResultList();
	}

	@Override
	public ClientEntity findById(Long id) {
		return em.find(ClientEntity.class, id);
	}

	@Override
	public void save(ClientEntity clientEntity) {
		if (clientEntity.getId() != null && clientEntity.getId() > 0) {
			em.merge(clientEntity);
		}
		em.persist(clientEntity);
	}

	@Override
	public void delete(Long id) {
		ClientEntity clientEntity = findById(id);
		em.remove(clientEntity);
	}

}
