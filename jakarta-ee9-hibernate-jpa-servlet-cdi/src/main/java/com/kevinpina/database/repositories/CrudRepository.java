package com.kevinpina.database.repositories;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {

	List<T> list() throws SQLException;

	T findById(Long id) throws SQLException;

	void save(T t) throws SQLException;

	void delete(Long id) throws SQLException;

}
