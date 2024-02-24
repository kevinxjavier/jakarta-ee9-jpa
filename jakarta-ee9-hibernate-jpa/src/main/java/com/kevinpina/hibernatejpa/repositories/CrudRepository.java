package com.kevinpina.hibernatejpa.repositories;

import java.util.List;

public interface CrudRepository<T> {

	List<T> list();

	T findById(Long id);

	void save(T t);

	void delete(Long id);

}
