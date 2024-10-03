package com.kevinpina.database.repositories;

import java.sql.SQLException;
import java.util.List;

import com.kevinpina.configs.Repositorio;
import com.kevinpina.model.entities.Category;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Repositorio
@RepositorioJPA
public class CategoryRepositoryJPAImpl implements CrudRepository<Category> {

//	Configured in ProducerResources.beanEntityManager()
	@Inject
	private EntityManager entityManager;

	@Override
	public List<Category> list() throws SQLException {
		return entityManager.createQuery("FROM Category", Category.class).getResultList();
	}

	@Override
	public Category findById(Long id) throws SQLException {
		return entityManager.find(Category.class, id);
	}

	@Override
	public void save(Category category) throws SQLException {
		if (category != null && category.getId() != null) { // before category.getId() > 0
			entityManager.merge(category);
		} else {
			entityManager.persist(category); 
			// if we use id = 0 will try to insert and element that doesn't exists 
			// and throws: org.hibernate.PersistentObjectException: detached entity passed to persist.
			// So if it is new we use id = null
		}

	}

	@Override
	public void delete(Long id) throws SQLException {
		entityManager.remove(findById(id));
	}

}
