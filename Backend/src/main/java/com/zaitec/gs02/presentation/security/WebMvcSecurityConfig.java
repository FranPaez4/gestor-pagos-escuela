package com.zaitec.gs02.presentation.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcSecurityConfig implements WebMvcConfigurer {

	private final AuthorizationInterceptor authorizationInterceptor;

	public WebMvcSecurityConfig(AuthorizationInterceptor authorizationInterceptor) {
		this.authorizationInterceptor = authorizationInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.authorizationInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/auth/login", "/auth/register", "/v3/api-docs/**", "/swagger-ui/**",
					"/swagger-ui.html");
	}

}
