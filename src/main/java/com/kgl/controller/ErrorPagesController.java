package com.kgl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Controller
public class ErrorPagesController {

	@ExceptionHandler(Exception.class)
	public ModelAndView trataExceptionGenerica(Exception exception) {
		System.out.println("Erro genérico acontecendo");
		exception.printStackTrace();

		ModelAndView modelAndView = new ModelAndView("error/500");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}

	@RequestMapping("/404")
	public String notFound() {
		return "/error/404";
	}

	@RequestMapping("/403")
	public String forbidden() {
		return "/error/403";
	}

	@RequestMapping("/500")
	public String internalServerError() {
		return "/error/500";
	}
}