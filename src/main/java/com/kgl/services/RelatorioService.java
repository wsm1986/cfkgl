package com.kgl.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kgl.models.Response;

public interface RelatorioService {
	
	public ModelAndView relatorioPagamento(Response response, HttpServletRequest request, RedirectAttributes attributes); 


}
