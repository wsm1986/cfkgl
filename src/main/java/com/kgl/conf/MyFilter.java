package com.kgl.conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Component
@Order(1)
public class MyFilter implements Filter {

	/**
	 * Logging todo o request da aplicação para auditoria
	 */
	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(MyFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		LOGGER.info("Iniciando filtro de logging");
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		LOGGER.warn("Destruindo o filtro de logging");
	}

}

