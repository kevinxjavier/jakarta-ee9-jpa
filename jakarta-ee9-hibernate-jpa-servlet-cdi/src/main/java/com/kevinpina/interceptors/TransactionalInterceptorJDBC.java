package com.kevinpina.interceptors;

import java.sql.Connection;
import java.util.logging.Logger;

import com.kevinpina.configs.MysqlConnectionPrincipal;
import com.kevinpina.exceptions.ServiceDatabaseException;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

// Always the Transactions are developed in a Service
@TransactionalJDBC
@Interceptor  // Register in WEB-INF/beans.xml
public class TransactionalInterceptorJDBC {

	@Inject
	@MysqlConnectionPrincipal
	private Connection connection;

	@Inject
	private Logger logger;

	@AroundInvoke
	public Object transactional(InvocationContext invocationContext) throws Exception {
		if (connection.getAutoCommit()) {
			connection.setAutoCommit(false);
		}

		logger.info("------------ Init Transaction " + invocationContext.getMethod().getName() + " from class "
				+ invocationContext.getMethod().getDeclaringClass());

		try {
			Object result = invocationContext.proceed();
			connection.commit();

			logger.info("------------ Finishing Transaction " + invocationContext.getMethod().getName() + " from class "
					+ invocationContext.getMethod().getDeclaringClass());
			return result;
		} catch (ServiceDatabaseException e) {
			connection.rollback();
			throw e;
		}
	}
}
