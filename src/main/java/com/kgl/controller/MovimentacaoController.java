package com.kgl.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kgl.models.Corretor;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;
import com.kgl.models.StatusMovimentacao;
import com.kgl.models.TipoPesquisaMovimentacao;
import com.kgl.repository.CorretorRepository;
import com.kgl.repository.EmployeeRepository;
import com.kgl.services.CorretorService;
import com.kgl.services.HomeBean;
import com.kgl.webservices.MovimentacaoRepository;

@Controller
@RequestMapping("/movimentacao")
public class MovimentacaoController {

	@Autowired
	MovimentacaoRepository dao;
	
	@Autowired
	private HomeBean home;
	
	@Autowired
	EmployeeRepository emp;
	
	@Autowired
	HttpSession sessao;
	
	@Autowired
	CorretorService corretorService;
	
	@RequestMapping({ "/form" })
	public ModelAndView form(Movimentacao movimentacao, HttpSession session) {
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		List<Movimentacao> mov = new ArrayList<>();
		if(home.permissaoUsuario()) {
			mov = (List<Movimentacao>) dao.findAll();
			//financeiro(dao.findByDtPagamentoBefore(new DateTime()),  session);
		}else {
			mov = dao.findByContratoCorretorEmail(home.emailLogado());
		}
		mvn.addObject("movimentacoes", mov);
		return mvn;
	}
	
	@RequestMapping(value = "/remover/{movimentacao}", method = RequestMethod.GET)
	public ModelAndView remover(@PathVariable("movimentacao") Movimentacao mov) {
		dao.delete(mov);
		return new ModelAndView("redirect:/contrato/detalharContr/"+mov.getContrato().getId()+"");

	}
	
	@RequestMapping(value = "/gerarPagamento/{movimentacao}/{flag}", method = RequestMethod.GET)
	public ModelAndView gerarPagamento(@PathVariable("movimentacao") Movimentacao mov, @PathVariable("flag") String flag, HttpSession session) {
		BigDecimal vlr = (BigDecimal) session.getAttribute("vlrCorretor");
		if("S".equals(flag)) {
			mov.setStatus(StatusMovimentacao.PAGO);
			session.setAttribute("vlrCorretor",  vlr.subtract(mov.getValorCorretor()));

		}else if("N".equals(flag)) {
			//session.setAttribute("vlrCorretor",  vlr.add(mov.getValorCorretor()));
			mov.setStatus(StatusMovimentacao.RECUSADO);
		}else if("A".equals(flag)) {
			session.setAttribute("vlrCorretor",  vlr.add(mov.getValorCorretor()));
			mov.setStatus(StatusMovimentacao.AGUARDADO_PAGAMENTO);
		}
		dao.save(mov);
		return new ModelAndView("redirect:/contrato/detalharContr/"+mov.getContrato().getId()+"");

	}
	
	@RequestMapping(value = "/atualizarLista", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON,
			produces =MediaType.APPLICATION_JSON)
	@ResponseBody 
	public ResponseEntity<List<Movimentacao>>  atualizarLista(@RequestBody String data) {
		Gson gson = new Gson();
		Response response = gson.fromJson(data, Response.class);
		List<Movimentacao> mov = new ArrayList<>();	
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.ENTRE)) {
				DateTime dt = formatter.parseDateTime(response.getDtInicial());
				DateTime dtF = formatter.parseDateTime(response.getDtFinal());
				mov = (List<Movimentacao>) dao.findByDtPagamentoBetween(dt, dtF);
			}else if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.CORRETOR)) {
				mov = dao.findByContratoCorretorId(Long.valueOf(response.getCorretor()));
				//mov = (List<Movimentacao>) dao.findByDtPagamentoBetweenAndContratoCorretorId(dt, dtF, Long.valueOf(response.getCorretor()));
			}else if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.APARTIR)) {
				DateTime dt = formatter.parseDateTime(response.getDtInicial());
				mov = (List<Movimentacao>) dao.findByDtPagamentoAfter(dt);
			}else {
				mov = (List<Movimentacao>) dao.findAll();
			}
			if (mov.size() == 0) {
				mov = new ArrayList<>();
			}
			financeiro(mov, sessao);
		} catch (Exception e) {
			System.out.println(data);
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<List<Movimentacao>>(mov, HttpStatus.OK);
		
	}
	

/*	@RequestMapping(value = "/atualizarLista/{dtPagamento}", method = RequestMethod.GET
	public ModelAndView   atualizarLista(@PathVariable("dtPagamento") String data, HttpSession session) {
		
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime dt = formatter.parseDateTime(data);
		DateTime dtF = formatter.parseDateTime("30"+data.substring(2));
		mvn.addObject("movimentacoes", 	dao.findByDtPagamentoBetween(dt, dtF));
		financeiro(dao.findByDtPagamentoBetween(dt, dtF), session);
		return mvn;
		
		List<Movimentacao> mov = new ArrayList<>();
		mov =  (List<Movimentacao>) dao.findAll();
		return new ResponseEntity<List<Movimentacao>>(mov,HttpStatus.OK);
		
	}
	*/
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
		session.setAttribute("vlrCorretor",  vlrCorretor);
		session.setAttribute("vlrLucro",  vlrLucro);
		session.setAttribute("vlrKgl",  vlrKgl);
		session.setAttribute("vlrContrato",  vlrContrato);
	
	}
	@ModelAttribute("corretores")
	public List<Corretor> listaCorretores() {
		return corretorService.todosCorretores();
	}

}
