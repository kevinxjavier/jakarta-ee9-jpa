package com.kevinpina.hibernatejpa.services;

import java.util.List;
import java.util.Optional;

import com.kevinpina.hibernatejpa.repository.entities.Client;

public interface ClientService {

	List<Client> list();

	Optional<Client> findById(Long id);

	void save(Client client);

	void delete(Long id);

}
