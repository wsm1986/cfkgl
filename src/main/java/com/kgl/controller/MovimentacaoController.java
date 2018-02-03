package com.kgl.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.google.gson.Gson;
import com.kgl.models.Corretor;
import com.kgl.models.Movimentacao;
import com.kgl.models.Relatorio;
import com.kgl.models.Response;
import com.kgl.enums.StatusMovimentacao;
import com.kgl.repository.EmployeeRepository;
import com.kgl.services.CorretorService;
import com.kgl.services.HomeBean;
import com.kgl.services.MovimentacaoService;

@Controller
@RequestMapping("/movimentacao")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)

public class MovimentacaoController {

	@Autowired
	MovimentacaoService movimentcaoService;

	@Autowired
	EmployeeRepository emp;

	@Autowired
	HttpSession sessao;

	@Autowired
	CorretorService corretorService;


	@RequestMapping({ "/form" })
	public ModelAndView form(Movimentacao movimentacao, HttpSession session) {
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		mvn.addObject("movimentacoes", movimentcaoService.buscarMovimentacoes());
		return mvn;
	}

	@RequestMapping(value = "/remover/{movimentacao}", method = RequestMethod.GET)
	public ModelAndView remover(@PathVariable("movimentacao") Movimentacao mov) {
		movimentcaoService.excluir(mov.getId());
		return new ModelAndView("redirect:/contrato/detalharContr/" + mov.getContrato().getId() + "");

	}

	@RequestMapping(value = "/gerarPagamento/{movimentacao}/{flag}", method = RequestMethod.GET)
	public ModelAndView gerarPagamento(@PathVariable("movimentacao") Movimentacao mov,
			@PathVariable("flag") String flag, HttpSession session) {
		BigDecimal vlr = (BigDecimal) session.getAttribute("vlrCorretor");
		if ("S".equals(flag)) {
			mov.setStatus(StatusMovimentacao.PAGO);
			session.setAttribute("vlrCorretor", vlr.subtract(mov.getValorCorretor()));

		} else if ("N".equals(flag)) {
			mov.setStatus(StatusMovimentacao.RECUSADO);
		} else if ("A".equals(flag)) {
			session.setAttribute("vlrCorretor", vlr.add(mov.getValorCorretor()));
			mov.setStatus(StatusMovimentacao.AGUARDADO_PAGAMENTO);
		}
		movimentcaoService.salvar(mov);
		return new ModelAndView("redirect:/contrato/detalharContr/" + mov.getContrato().getId() + "");

	}

	@RequestMapping(value = "/atualizarLista", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<List<Movimentacao>> atualizarLista(@RequestBody String data) {
		Gson gson = new Gson();
		Response response = gson.fromJson(data, Response.class);
		List<Movimentacao> mov = new ArrayList<>();
		try {
			mov = movimentcaoService.buscarMovimentacao(response);

			if (mov.size() == 0) {
				mov = new ArrayList<>();
			}
		} catch (Exception e) {
			System.out.println(data);
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<List<Movimentacao>>(mov, HttpStatus.OK);

	}


	

	public void financeiro(List<Movimentacao> movs, HttpSession session) {
		BigDecimal vlrLucro = new BigDecimal(0);
		BigDecimal vlrCorretor = new BigDecimal(0);
		BigDecimal vlrKgl = new BigDecimal(0);
		BigDecimal vlrContrato = new BigDecimal(0);

		for (Movimentacao movimentacao : movs) {
			vlrLucro = vlrLucro.add(movimentacao.getLucro());
			vlrCorretor = vlrCorretor.add(movimentacao.getValorCorretor());
			vlrKgl = vlrKgl.add(movimentacao.getValorKgl());
			vlrContrato = vlrContrato.add(movimentacao.getContrato().getValor());

		}
		session.setAttribute("vlrCorretor", vlrCorretor);
		session.setAttribute("vlrLucro", vlrLucro);
		session.setAttribute("vlrKgl", vlrKgl);
		session.setAttribute("vlrContrato", vlrContrato);

	}

	@ModelAttribute("corretores")
	public List<Corretor> listaCorretores() {
		return corretorService.todosCorretores();
	}

}
