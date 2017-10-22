package com.kgl.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.StatusMovimentacao;
import com.kgl.services.HomeBean;
import com.kgl.webservices.MovimentacaoRepository;

@Controller
@RequestMapping("/movimentacao")
public class MovimentacaoController {

	@Autowired
	MovimentacaoRepository dao;
	
	@Autowired
	private HomeBean home;
	
	@RequestMapping({ "/form" })
	public ModelAndView form(Movimentacao movimentacao, HttpSession session) {
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		List<Movimentacao> mov = new ArrayList<>();
		if(home.permissaoUsuario()) {
			mov = (List<Movimentacao>) dao.findAll();
			financeiro(mov,  session);
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
