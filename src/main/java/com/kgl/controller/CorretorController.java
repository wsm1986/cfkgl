package com.kgl.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.kgl.models.Banco;
import com.kgl.models.Corretor;
import com.kgl.models.GenerateHashPasswordUtil;
import com.kgl.models.Movimentacao;
import com.kgl.models.Relatorio;
import com.kgl.models.Role;
import com.kgl.models.User;
import com.kgl.services.CorretorService;
import com.kgl.services.MovimentacaoService;
import com.kgl.services.UsuarioService;
import com.kgl.validator.CorretorValidator;

@Controller
@RequestMapping("/corretor")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)

public class CorretorController {

	@Autowired
	private CorretorService dao;

	@Autowired
	private CorretorValidator corretorValidation;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	MovimentacaoService movimentcaoService;

	@Autowired
	private ApplicationContext appContext;

	@RequestMapping({ "/", "/form" })
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
		dao.salvar(corretor);
		insertUser(corretor.getEmail());
		return listar();
	}

	@RequestMapping("/listar")
	private ModelAndView listar() {
		ModelAndView mvn = new ModelAndView("corretor/listar");
		mvn.addObject("corretores", dao.todosCorretores());

		return mvn;
	}

	@RequestMapping("/deletar/{id}")
	private ModelAndView deletar(@PathVariable("id") Long id) {
		dao.excluir(dao.buscarCorretor(id).getId());
		return listar();
	}

	@RequestMapping("/detalhe/{id}")
	private ModelAndView detalhe(@PathVariable("id") Long id) {
		ModelAndView mvn = new ModelAndView("corretor/detalhe");
		mvn.addObject("corretor", dao.buscarCorretor(id));
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

	public void insertUser(String userName) {
		String password = GenerateHashPasswordUtil.generateHash("1234");
		List<Role> list = new ArrayList();
		Role role = new Role();
		role.setNome("ROLE_USER");
		list.add(role);
		User user = new User(userName, password, list);
		usuarioService.salvar(user);
	}

	@RequestMapping(value = "/pdf/", method = RequestMethod.GET)
	public ModelAndView imprimirPdf() {
		JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:relatorioKgl.jrxml");
		view.setApplicationContext(appContext);
		Map<String, Object> params = new HashMap<>();

		List<Movimentacao> list = movimentcaoService.buscarMovimentacoes();
		List<Relatorio> relatorio = new ArrayList<>();
		for (Movimentacao movimentacao : list) {
			Relatorio addLista = new Relatorio();
			addLista.setDataPagamento(movimentacao.getDtPagamento().toString());
			addLista.setLucro(movimentacao.getFormatarValorLucro());
			addLista.setProposta(movimentacao.getContrato().getCodigoContrato().toString());
			addLista.setStatus(movimentacao.getStatus().toString());
			addLista.setValorContrato(movimentacao.getFormatarValorContrato());
			addLista.setValorCorretor(movimentacao.getFormatarValorCorretor().toString());
			addLista.setValorKgl(movimentacao.getFormatarValorKgl());

			relatorio.add(addLista);
		}

		params.put("datasource", relatorio);
		params.put("rg_inquilino", String.valueOf(1516));

		return new ModelAndView(view, params);
	}

}
