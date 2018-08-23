/**
 * 
 */
package com.ugo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.classic.Logger;

/**
 * @author qipeng
 * PC端拦截
 */
public class HandleInterceptor implements HandlerInterceptor{
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(HandleInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		if(requestURI.indexOf("login")>0) {
			System.out.println("interceptor:首次跳转到login页!");
			response.sendRedirect("/login.jsp");
			return false;
		}
		String userId = (String) request.getSession().getAttribute("userId");
		logger.info("访问用户id:"+userId);
		if(userId == null) {
			System.out.println("interceptor:登录无效跳转到login页!");
			response.sendRedirect("/login.jsp");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
