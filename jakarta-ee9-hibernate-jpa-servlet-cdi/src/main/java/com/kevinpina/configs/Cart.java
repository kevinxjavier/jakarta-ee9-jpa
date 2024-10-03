package com.kevinpina.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;

@SessionScoped
@Named	// In CDI by default the name is cammel case so it not necessary define one.
		// We don't define as @Named("shoppingCart") because other classes can use this Annotation 
		// and if we do it will throw DefinitionException("Cannot specify a value for @Named").
@Stereotype	// Not only use for semantic as the @Qualifier but also allows for define contexts a @SessionScoped
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cart {

}
