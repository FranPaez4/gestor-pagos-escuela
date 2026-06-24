package com.zaitec.gs02.presentation.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Authorize {

	/**
	 * Roles allowed to access. Empty means: any authenticated user.
	 * @return the roles allowed to access
	 */
	String[] roles() default {};

}
