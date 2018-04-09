package com.kgl.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Corretor;
import com.kgl.models.MessageWeb;
import com.kgl.models.Movimentacao;
import com.kgl.models.Produto;
import com.kgl.models.Segurado;
import com.kgl.enums.StatusContrato;
import com.kgl.enums.StatusMovimentacao;
import com.kgl.models.SubProduto;
import com.kgl.services.ContratoService;
import com.kgl.services.CorretorService;
import com.kgl.services.MovimentacaoService;
import com.kgl.services.ProdutoService;
import com.kgl.services.impl.CriadorDeContrato;
import com.kgl.validator.ContratoValidator;
import com.kgl.webservices.SubProdutoRepository;

@Controller
@RequestMapping("/contrato")
public class ContratoKglController {

	@Autowired
	CorretorService corretorService;

	@Autowired
	private SubProdutoRepository subProdutoDao;

	@Autowired
	CriadorDeContrato contratoNovo;

	@Autowired
	ContratoService contratoService;

	@Autowired
	MovimentacaoService movService;

	@Autowired
	private ContratoValidator contratoValidation;

	@Autowired
	ProdutoService produtoService;

	@Autowired
	SubProdutoRepository daoSubProduto;

	@RequestMapping({ "/", "/form" })
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		return mvn;

	}

	@RequestMapping({ "/listar" })
	private ModelAndView listar() {
		ModelAndView mvn = new ModelAndView("contrato/listar");
		return mvn;

	}

	@RequestMapping({ "/salvar" })
	private ModelAndView salvar(@Valid Contrato contrato, BindingResult result) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		try {
			if (result.hasErrors()) {
				return form(contrato);
			}
			contratoNovo.implantarContrato(contrato);
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.SUCCESS_SAVE);

		} catch (Exception e) {
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_SAVE);
		}
		return mvn;
	}

	@RequestMapping({ "/salvarSubProduto/{produto}/{descricao}" })
	public ModelAndView salvar(@PathVariable("produto") Long id, @PathVariable("descricao") String desc) {
		SubProduto subProduto = new SubProduto();
		Produto p = new Produto();
		p.setId(id);
		subProduto.setProduto(p);
		subProduto.setDescricao(desc);
		subProdutoDao.save(subProduto);
		return new ModelAndView("redirect:/contrato/form");
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator('.');
		dfs.setDecimalSeparator(',');
		df.setDecimalFormatSymbols(dfs);
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, df, true));
		binder.setValidator(contratoValidation);

	}

	@RequestMapping("/addSegurado")
	private ModelAndView addSegurado(Segurado segurado) {
		System.out.println(segurado.getNome());
		ModelAndView mvn = new ModelAndView("redirect:/contrato/form");
		return mvn;
	}

	@RequestMapping(value = "/addSegurado/{segurado}", method = RequestMethod.POST)
	public String testaImpressora(@PathVariable("segurado") String segurado) {
		System.out.println(segurado);
		return "redirect:/contrato/form";
	}

	@RequestMapping(value = "/detalharContr/{id}", method = RequestMethod.GET)
	public ModelAndView detalharContr(@PathVariable("id") Long id) {
		ModelAndView mvn = new ModelAndView("contrato/listar");
		try {
			Contrato contrato = contratoService.buscarContrato(id);
			if (contrato == null) {
				mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_FIND);
				return mvn;
			}
			mvn.addObject("contratos", contrato);
			movService.atualizarParcelaMov(contrato);
			mvn.addObject("movimentacoes", movService.findByContrato(contratoService.buscarContrato(id)));

		} catch (Exception e) {
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_FIND);
		}

		return mvn;
	}

		
	@RequestMapping(value = "/remover/{contrato}", method = RequestMethod.GET)
	public ModelAndView remover(@PathVariable("contrato") Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/listar");

		try {
			List<Movimentacao> list = movService.findByContrato(contrato);
			for (Movimentacao movimentacao : list) {
				movService.excluir(movimentacao.getId());
			}
			contratoNovo.implantarContrato(contrato);
			contratoService.excluir(contrato.getId());

			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.SUCCESS_DELETE);

		} catch (Exception e) {
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_DELETE);
		}
		return mvn;
	}

	@RequestMapping(value = "/update/recusar/{contrato}", method = RequestMethod.GET)
	public ModelAndView recusar(@PathVariable("contrato") Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/listar");

		try {
			contrato.setStatusContrato(StatusContrato.RECUSADO);
			contratoService.salvar(contrato);
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.SUCCESS_ALTER);

		} catch (Exception e) {
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_ALTER);
		}
		return mvn;
	}

	@RequestMapping(value = "/update/implantar/{contrato}", method = RequestMethod.GET)
	public ModelAndView implantar(@PathVariable("contrato") Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/listar");

		try {
			contrato.setStatusContrato(StatusContrato.IMPLANTADO);
			contratoService.salvar(contrato);
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.SUCCESS_ALTER);

		} catch (Exception e) {
			mvn.addObject(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_ALTER);
		}
		return mvn;

	}

	@RequestMapping(value = "/adiantamento/", method = RequestMethod.POST)
	public ModelAndView adiantamento(Movimentacao movimentacao) {
		Movimentacao update = movService.findById(movimentacao.getId());

		if (null != movimentacao.getAdiantamento() && movimentacao.getAdiantamento().intValue() != 0) {
			update.setAdiantamento(movimentacao.getAdiantamento());
			update.setStatus(movimentacao.getAdiantamento().intValue() != 0 ? StatusMovimentacao.PARCIAL
					: StatusMovimentacao.AGUARDADO_PAGAMENTO);
		} else {
			update.setStatus(StatusMovimentacao.AGUARDADO_PAGAMENTO);

		}
		movService.salvar(update);

		return detalharContr(update.getContrato().getId());

	}

	@ModelAttribute("corretores")
	public List<Corretor> listaCorretores() {
		return corretorService.todosCorretores();
	}

	@ModelAttribute("contratos")
	public List<Contrato> contratos() {
		return contratoService.buscarContrato();
	}

	@ModelAttribute("subProdutos")
	public List<SubProduto> listaSubProdutos() {
		return (List<SubProduto>) subProdutoDao.findAll();
	}

	@ModelAttribute("produtos")
	public List<Produto> listaProdutos() {
		// return (List<Produto>) produtoService.produtos();
		return (List<Produto>) produtoService.produtosAtivos();

	}

	@ModelAttribute("novoSubProduto")
	public SubProduto novoSubProduto() {
		return new SubProduto();
	}

	@ModelAttribute("mov")
	public Movimentacao Movimentacao() {
		return new Movimentacao();
	}
}
