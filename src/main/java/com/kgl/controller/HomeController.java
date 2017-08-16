package com.kgl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;

@Controller
public class HomeController {

	@RequestMapping({ "/", "/index" })
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("index");
		return mvn;

	}	
	

}
