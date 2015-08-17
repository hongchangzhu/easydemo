package com.framework.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BaseHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(BaseHandlerInterceptorAdapter.class);
	/**
	 * 拦截器是单例，因此不管用户请求多少次都只有一个拦截器实现，即线程不安全。
	 * 這個時候則使用ThreadLocal，它是线程绑定的变量，提供线程局部变量（
	 * 一个线程一个ThreadLocal，A线程的ThreadLocal只能看到A线程的ThreadLocal，不能看到B线程的ThreadLocal）
	 */
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("BaseWatch");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();// 2、结束时间
		long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		long consumeTime = endTime - beginTime;// 3、消耗的时间
		logger.info(String.format("The resource '%s' consume %d millis when executed.", request.getRequestURI(),
				consumeTime));
	}

	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	}
}
