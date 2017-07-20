package com.kgl.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Banco;
import com.kgl.models.Corretor;
import com.kgl.repository.CorretorRepository;
import com.kgl.validator.CorretorValidator;

@Controller
@RequestMapping("/corretor")
public class CorretorController {

	@Autowired
	private CorretorRepository dao;

	@Autowired
	private CorretorValidator corretorValidation;

	@RequestMapping("/form")
	private ModelAndView form(Corretor Corretor) {
		ModelAndView mvn = new ModelAndView("corretor/novo");
		mvn.addObject("corretor", Corretor);
		mvn.addObject("bancos", Banco.values());
		return mvn;
	}

	@RequestMapping("/novo")
	private ModelAndView novo(@Valid Corretor corretor, BindingResult result) {
		if (result.hasErrors()) {
			return form(corretor);
		}
		corretor.getConta().setTitular(corretor.getNome());
		corretor.setDtInclusao(Calendar.getInstance());
		dao.save(corretor);
		return listar();
	}
	
	@RequestMapping("/listar")
	private ModelAndView listar() {
		ModelAndView mvn = new ModelAndView("corretor/listar");
		mvn.addObject("corretores", dao.findAll());
		return mvn;
	}

	@RequestMapping("/deletar/{id}")
	private ModelAndView deletar(@PathVariable("id") Long id) {
		dao.delete(dao.findOne(id));
		return listar();
	}
	
	@RequestMapping("/detalhe/{id}")
	private ModelAndView detalhe(@PathVariable("id") Long id) {
		ModelAndView mvn = new ModelAndView("corretor/detalhe");
		mvn.addObject("corretor", dao.findOne(id));
		return mvn;
	}
	@RequestMapping("/update/{corretor}")
	private ModelAndView update(@PathVariable("corretor") Corretor corretor) {
		ModelAndView mvn = new ModelAndView("corretor/update");
		mvn.addObject("corretor", corretor);
		mvn.addObject("bancos", Banco.values());
		return mvn;
	}	
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.setValidator(corretorValidation);
	}
}
