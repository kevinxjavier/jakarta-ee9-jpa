package com.kevinpina.interceptors;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.kevinpina.model.entities.Category;
import com.kevinpina.model.entities.Product;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Logging
@Interceptor  // Register in WEB-INF/beans.xml
public class LoggingInterceptor {

	@Inject
	private Logger log;

	@AroundInvoke
	public Object logging(InvocationContext invocationContext) throws Exception {
		log.info("------------ Invoking execution method " + invocationContext.getMethod().getName() + " from class "
				+ invocationContext.getMethod().getDeclaringClass());

		Object result = invocationContext.proceed();

		log.info("------------ Argument parameters of executing method " + invocationContext.getMethod().getName());
		for (Object param : invocationContext.getParameters()) {
			log.info("Argument = [" + param + "]");
		}

		log.info("------------ Result of executing method " + invocationContext.getMethod().getName());
		if (result instanceof List) {
			if ("class com.kevinpina.service.CategoryServiceImpl".equals(invocationContext.getMethod().getDeclaringClass().toString())) {
				((List<Category>) result).forEach(p -> log.info("value = [" + p + "]"));
			} else {
				((List<Product>) result).forEach(p -> log.info("value = [" + p + "]"));
			}
		} else if (result instanceof Optional) {
			log.info("value = [" + ((Optional<Product>) result).get() + "]");
		}

		log.info("------------ Finishing execution method " + invocationContext.getMethod().getName() + " from class "
				+ invocationContext.getMethod().getDeclaringClass());

		return result;
	}

}
