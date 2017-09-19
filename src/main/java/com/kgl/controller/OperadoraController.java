package com.kgl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kgl.models.MessageWeb;
import com.kgl.models.Operadora;
import com.kgl.webservices.OperadoraRepository;



@Controller
@RequestMapping("/operadora")
public class OperadoraController {

	@Autowired
	OperadoraRepository dao; 
	
	@RequestMapping({ "/form" })
	public ModelAndView form(Operadora operadora) {
		ModelAndView mvn = new ModelAndView("operadora/novo");
		mvn.addObject("operadora", operadora);
		mvn.addObject("operadoras", dao.findAll());
		return mvn;
	}
	
	@RequestMapping({ "/salvar" })
	public ModelAndView salvar(@Valid Operadora operadora, BindingResult result,HttpServletRequest request, RedirectAttributes attributes) {
		String referer = request.getHeader("Referer");
		if (result.hasErrors()) {
			return form(operadora);
		}
		
		dao.save(operadora);

		attributes.addFlashAttribute(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.SUCCESS_ALTER);

		return new ModelAndView("redirect:" + referer);
	}
	@RequestMapping("/remover/{id}")
	private ModelAndView remover(@PathVariable("id") Long id, HttpServletRequest request) {
		String referer = request.getHeader("Referer");

		dao.delete(id);
		return new ModelAndView("redirect:" + referer);
	}
	
	@RequestMapping("/listar")
	private ModelAndView listar() {
		ModelAndView mvn = new ModelAndView("operadora/operadoras");
		return mvn;
	}
	
	@RequestMapping("/teste")
	private ModelAndView teste() {
		ModelAndView mvn = new ModelAndView("index2");
		return mvn;
	}
}
