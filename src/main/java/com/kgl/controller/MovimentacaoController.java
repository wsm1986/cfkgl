package com.kgl.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.h2.util.New;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Employee;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;
import com.kgl.models.StatusMovimentacao;
import com.kgl.repository.EmployeeRepository;
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
	
	@RequestMapping({ "/form" })
	public ModelAndView form(Movimentacao movimentacao, HttpSession session) {
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		List<Movimentacao> mov = new ArrayList<>();
		if(home.permissaoUsuario()) {
			mov = (List<Movimentacao>) dao.findAll();
			financeiro(dao.findByDtPagamentoBefore(new DateTime()),  session);
		}else {
			mov = dao.findByContratoCorretorEmail(home.emailLogado());
		}
		mvn.addObject("movimentacoes", mov);
		
		mvn.addObject("dtPagamento", "");;
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
		String novaDt = data.substring(5);
		List<Movimentacao> mov = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime dt = formatter.parseDateTime(novaDt);
		DateTime dtF = formatter.parseDateTime("30"+novaDt.substring(2));
		mov =  (List<Movimentacao>) dao.findByDtPagamentoBetween(dt, dtF);
		if(mov.size() == 0) {
			mov =	 new ArrayList<>();
		}
		financeiro(mov, sessao);
		sessao.setAttribute("vlrCorretor",  "123");

		return new ResponseEntity<List<Movimentacao>>(mov,HttpStatus.OK);
		
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

}
