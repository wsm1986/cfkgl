package com.kgl.controller;

import java.math.BigDecimal;

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
	public ModelAndView form(Movimentacao movimentacao) {
		ModelAndView mvn = new ModelAndView("movimentacao/novo");
		if(home.permissaoUsuario()) {
			mvn.addObject("movimentacoes", dao.findAll());
		}else {
			mvn.addObject("movimentacoes", dao.findByContratoCorretorEmail(home.emailLogado()));
		}
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

}
