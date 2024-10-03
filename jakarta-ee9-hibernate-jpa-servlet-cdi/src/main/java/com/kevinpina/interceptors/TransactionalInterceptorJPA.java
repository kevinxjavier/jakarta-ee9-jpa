package com.kevinpina.interceptors;

import java.util.logging.Logger;

import com.kevinpina.exceptions.ServiceDatabaseException;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;

// Always the Transactions are developed in a Service
@TransactionalJPA
@Interceptor // Register in WEB-INF/beans.xml
public class TransactionalInterceptorJPA {

	@Inject
	private EntityManager connection;

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object transactional(InvocationContext invocationContext) throws Exception {
		
		logger.info("------------ Init Transaction " + invocationContext.getMethod().getName() + " from class "
				+ invocationContext.getMethod().getDeclaringClass());

		try {
			connection.getTransaction().begin();
			
			Object result = invocationContext.proceed();
			
			connection.getTransaction().commit();

			logger.info("------------ Finishing Transaction " + invocationContext.getMethod().getName() + " from class "
					+ invocationContext.getMethod().getDeclaringClass());
			return result;
		} catch (ServiceDatabaseException e) {
			connection.getTransaction().rollback();
			throw e;
		}
	}
}
