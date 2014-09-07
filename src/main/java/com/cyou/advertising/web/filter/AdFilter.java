package com.cyou.advertising.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyou.advertising.web.common.Constants;
import com.cyou.advertising.web.model.security.Users;

/**
 * 
 * <一句话功能简述> <功能详细描述>
 * 
 * @author zhangtao
 * @version [版本号, 2014-8-7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AdFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//String url = request.getRequestURL().toString();
		String uri = request.getRequestURI().replaceAll("/$", "");
		
		if (!uri.equals(request.getContextPath() + "/user/manage/index")) {
			Users users = (Users) request.getSession().getAttribute(Constants.LOGIN_USER);
			if (users == null) {
				response.sendRedirect(request.getContextPath() + "/user/manage/logout");
				return;
			}
		}

		filterChain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
