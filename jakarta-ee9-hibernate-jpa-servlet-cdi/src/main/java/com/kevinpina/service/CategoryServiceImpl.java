package com.kevinpina.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.kevinpina.configs.MysqlConnectionPrincipal;
import com.kevinpina.database.repositories.CategoryRepositoryJDBCImpl;
import com.kevinpina.database.repositories.CrudRepository;
import com.kevinpina.exceptions.ServiceDatabaseException;
import com.kevinpina.interceptors.Logging;
import com.kevinpina.model.entities.Category;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CategoryServiceImpl implements Service<Category> {

	CrudRepository<Category> repository;

	@Inject
	public CategoryServiceImpl(@MysqlConnectionPrincipal /* Or use @Named("beanConnection")*/ Connection connection) {
		repository = new CategoryRepositoryJDBCImpl(connection);
	}

	@Logging
	@Override
	public List<Category> list() {
		try {
			return repository.list();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceDatabaseException(e.getMessage(), e.getCause());
		}
	}

	@Logging
	@Override
	public Optional<Category> findById(Long id) {
		try {
			return Optional.ofNullable(repository.findById(id));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceDatabaseException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void save(Category category) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

}
