package com.kevinpina.hibernatejpa.services;

import java.util.List;
import java.util.Optional;

import com.kevinpina.hibernatejpa.repository.entities.ClientEntity;

public interface ClientService {

	List<ClientEntity> list();

	Optional<ClientEntity> findById(Long id);

	void save(ClientEntity clientEntity);

	void delete(Long id);

}
