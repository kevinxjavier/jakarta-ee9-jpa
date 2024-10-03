package com.kevinpina.configs;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.inject.Qualifier;

@Qualifier /* Qualifier it is create a customized Annotation with @Qualifier so we dont use @Name("")*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD, PARAMETER, TYPE, CONSTRUCTOR })
public @interface MysqlConnectionPrincipal {

}
