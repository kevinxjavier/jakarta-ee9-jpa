package com.kevinpina.configs;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;

@ApplicationScoped
@Named	// In CDI by default the name is cammel case so it not necessary define one.
		// We don't define as @Named("shoppingCart") because other classes can use this Annotation.
@Stereotype	// Not only use for semantic as the @Qualifier but also allows for define contexts a @ApplicationScoped
@Retention(RUNTIME)
@Target(TYPE)
public @interface Repositorio {

}
