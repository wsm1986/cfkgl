package com.kgl.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		return mvn;
	}
	
	@RequestMapping({ "/salvar" })
	public ModelAndView salvar(@Valid Operadora operadora, BindingResult result) {
		if (result.hasErrors()) {
			return form(operadora);
		}
		
		dao.save(operadora);
		return form(operadora);
	}
	@RequestMapping("/remover/{operadora}")
	private ModelAndView remover(@PathVariable("operadora") Operadora operadora) {
		dao.delete(operadora);
		return form(operadora);
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
