package com.kevinpina.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {

	List<T> list();

	Optional<T> findById(Long id);

	void save(T product);

	void delete(Long id);

}
