package com.kgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Movimentacao;
import com.kgl.webservices.MovimentacaoRepository;

@Controller
@RequestMapping("/movimentacao")
public class MovimentacaoController {

	@Autowired
	MovimentacaoRepository dao;

	@RequestMapping({ "/form" })
	public ModelAndView form(Movimentacao movimentacao) {
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		mvn.addObject("movimentacoes", dao.findAll());
		return mvn;
	}

}
