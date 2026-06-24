package com.zaitec.gs02.presentation.security;

import com.zaitec.gs02.domain.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String ATTRIBUTE_NAME = "authenticatedUser";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class) && parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

		return request.getAttribute(ATTRIBUTE_NAME);
	}

}
