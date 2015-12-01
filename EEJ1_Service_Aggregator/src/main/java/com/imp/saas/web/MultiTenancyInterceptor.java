package com.imp.saas.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * This class intercept each request to set and get attribute "CURRENT_TENANT_IDENTIFIER"
 *  from each request it get "tenantid" from the URL so that Respective tenantid can be used
 * @author rakesh.singhania
 *
 */
public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		if (pathVars.containsKey("tenantid")) {
			req.setAttribute("CURRENT_TENANT_IDENTIFIER", pathVars.get("tenantid"));
		}
		return true;
	}
}
