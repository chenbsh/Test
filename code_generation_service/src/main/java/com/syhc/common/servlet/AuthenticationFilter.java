package com.syhc.common.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthenticationFilter implements Filter {

	private Log logger = LogFactory.getLog(this.getClass().getName());

	public AuthenticationFilter() {
		super();
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		String url = hrequest.getRequestURI();

		System.out.println("\n\n\n" + request.getLocalAddr() + ":" + request.getLocalPort() + url);

		chain.doFilter(hrequest, hresponse);
		return;
	}

	public void destroy() {
		logger.warn("----------应用服务器停止并关闭过滤器----------");
	}
}
