package com.kevinpina.database.repositories;

import java.sql.SQLException;
import java.util.List;

import com.kevinpina.configs.Repositorio;
import com.kevinpina.model.entities.Product;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Repositorio
@RepositorioJPA
public class ProductRepositoryJPAImpl implements CrudRepository<Product> {

//	Configured in ProducerResources.beanEntityManager()
	@Inject
	private EntityManager entityManager;

	@Override
	public List<Product> list() throws SQLException {
//		return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList(); // Down is the same but resumed
//		return entityManager.createQuery("FROM Product", Product.class).getResultList();
		
		// With this works better due to instead of call getCategory for every different catergory, this will bring in one query all together.
		return entityManager.createQuery("SELECT p FROM Product p left outer join fetch p.category", Product.class).getResultList();
	}

	@Override
	public Product findById(Long id) throws SQLException {
		return entityManager.find(Product.class, id);
	}

	@Override
	public void save(Product product) throws SQLException {
		if (product != null && product.getId() != null) { // before product.getId() > 0
			entityManager.merge(product);
		} else {
			entityManager.persist(product); 
			// if we use id = 0 will try to insert and element that doesn't exists 
			// and throws: org.hibernate.PersistentObjectException: detached entity passed to persist.
			// So if it is new we use id = null
		}

	}

	@Override
	public void delete(Long id) throws SQLException {
		entityManager.remove(findById(id));
	}
	
	public Product findByName(String name) {
		return entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
				.setParameter("name", name)
				.getSingleResult();
	}

}
