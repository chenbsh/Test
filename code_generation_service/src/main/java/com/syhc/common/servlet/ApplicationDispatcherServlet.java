package com.syhc.common.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationDispatcherServlet extends DispatcherServlet {

	private static ApplicationContext context = null;

	private static final long serialVersionUID = -258000902319316749L;

	protected void initStrategies(ApplicationContext context) {
		super.initStrategies(context);
		ApplicationDispatcherServlet.context = context;
	}

	public static Object getBean(String beanName) {
		if (context == null) {
			return null;
		}
		return context.getBean(beanName);
	}
}
