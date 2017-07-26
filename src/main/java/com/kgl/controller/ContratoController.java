package com.kgl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Banco;
import com.kgl.models.Contrato;
import com.kgl.repository.CorretorRepository;
import com.kgl.webservices.OperadoraRepository;

@Controller
@RequestMapping("/contrato")
public class ContratoController {

	@Autowired
	private CorretorRepository corretorDao;
	
	@Autowired
	private OperadoraRepository operadoraDao;


	@RequestMapping({ "/", "/form"})
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		mvn.addObject("corretores", corretorDao.findAll());
		mvn.addObject("bancos", Banco.values());
		mvn.addObject("operadoras", operadoraDao.findAll());
		return mvn;
	}}
