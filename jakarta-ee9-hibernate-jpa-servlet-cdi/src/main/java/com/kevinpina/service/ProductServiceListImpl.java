package com.kevinpina.service;

import java.util.List;
import java.util.Optional;

import com.kevinpina.model.entities.Category;
import com.kevinpina.model.entities.Product;

import jakarta.enterprise.inject.Alternative;

/**
 * If this class is not annotated with @Alternative will throws this exception:
 *	  	 Possible dependencies:
 *	  	  - Managed Bean [class com.kevinpina.service.ProductServiceIJdbcmpl] with qualifiers [@Any @Default],
 *	  	  - Managed Bean [class com.kevinpina.service.ProductServiceImpl] with qualifiers [@Any @Default]
 *
 * This is beacause we have 2 classes implementing the same interface and Service<Product> and Java at Runtime
 * does not know how to solve it.
 * 
 *  1)    So one way: is using @Alternative annotation in this class and it means that the class ProductServiceImpl.java is by default injected 
 *        when we use @Inject Service<Product> serviceProduct.
 *        
 *  2)    The second way is using @Named("beanProductServiceIJdbcmpl") in combination with @ApplicationScoped in this class ProductServiceIJdbcmpl.java 
 *        and in the other class ProductFormSevlet.java we use as an attribute @Inject in combination with [@Named("beanProductServiceImpl") or 
 *        @Named("beanProductServiceIJdbcmpl")] Service<Product> serviceProduct depending of which you want to inject.
 *     
 *  Note) in WEB-INF/beans.xml it is bean-discovery-mode="all" means not only annotated classes will be CDI also classes 
 *        without annotions will be CDI and Depandant by default.
 */

//@Alternative
public class ProductServiceListImpl implements Service<Product> {

	private static final String NOT_NEEDED = "Not needed!";

	@Override
	public List<Product> list() {
		throw new UnsupportedOperationException(NOT_NEEDED);
	}

	@Override
	public Optional<Product> findById(Long id) {
		throw new UnsupportedOperationException(NOT_NEEDED);
	}

	@Override
	public void save(Product product) {
		throw new UnsupportedOperationException(NOT_NEEDED);
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException(NOT_NEEDED);
	}

}
