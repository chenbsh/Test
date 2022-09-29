package com.syhc.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 超级父类业务逻辑控制器
 * 
 * @Copyright MacChen
 * 
 * @Project CodeGenerationTool
 * 
 * @Author MacChen
 * 
 * @timer 2017-12-01
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 8.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class ParentController extends HttpServlet {

	private static final long serialVersionUID = -7934307040345692217L;

	public void setAttribute(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	public Object getAttribute(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	public void removeAttribute(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	public void alert(HttpServletRequest request, String value) {
		request.getSession().setAttribute("alertMsg", value);
	}

	// ------------------------------------------------------------------------------------
	public void outTextString(HttpServletResponse response, String source) {
		try {
			response.setContentType("application/text;charset=UTF-8");
			response.setContentLength(source.getBytes("utf-8").length);
			PrintWriter out = response.getWriter();
			out.write(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outXMLString(HttpServletResponse response, String source) {
		try {
			response.setContentType("application/xml;charset=UTF-8");
			response.setContentLength(source.getBytes("utf-8").length);
			PrintWriter out = response.getWriter();
			out.write(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outHTMLString(HttpServletResponse response, String source) {
		try {
			response.setContentType("application/html;charset=UTF-8");
			response.setContentLength(source.getBytes("utf-8").length);
			PrintWriter out = response.getWriter();
			out.write(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outJsonString(HttpServletResponse response, String source) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setContentLength(source.getBytes("utf-8").length);
			PrintWriter out = response.getWriter();
			out.write(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip.indexOf("unknown,") != -1) {
			ip = ip.replaceAll("unknown,", "");
		}
		if (ip.indexOf(",unknown") != -1) {
			ip = ip.replaceAll(",unknown", "");
		}
		if (ip.indexOf(",") != -1) {
			String array[] = ip.split(",");
			for (String temp : array) {
				if (StringUtils.isNotBlank(temp)) {
					ip = temp;
					break;
				}
			}
		}
		return ip;
	}
}
