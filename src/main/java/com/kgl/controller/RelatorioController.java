package com.kgl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kgl.enums.StatusMovimentacao;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;
import com.kgl.services.CorretorService;
import com.kgl.services.RelatorioService;

@Controller
@RequestMapping("/relatorio")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)

public class RelatorioController {
	@Autowired
	CorretorService corretorService;

	@Autowired
	RelatorioService relatorio;

	@RequestMapping({ "/form" })
	public ModelAndView gerarRelatorio(Movimentacao movimentacao, HttpSession session) {
		ModelAndView mvn = new ModelAndView("movimentacao/relatorio");
		mvn.addObject("response", new Response());
		mvn.addObject("corretores", corretorService.todosCorretores());

		mvn.addObject("status", StatusMovimentacao.values());

		return mvn;
	}

	@RequestMapping(value = "/pdf/", method = RequestMethod.POST)
	public ModelAndView imprimirPdf(Response response, HttpServletRequest request, RedirectAttributes attributes) {
		return relatorio.relatorioPagamento(response, request, attributes);
	}

}
