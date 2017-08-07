package com.kgl.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.CategoriaProduto;
import com.kgl.models.Produto;
import com.kgl.models.TabelaComissao;
import com.kgl.validator.ProdutoValidator;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;
import com.kgl.webservices.SubProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoRepository dao;

	@Autowired
	OperadoraRepository daoOperadora;

	@Autowired
	SubProdutoRepository daoSubProduto;

	@Autowired
	private ProdutoValidator produtoValidation;

	@RequestMapping({ "/form" })
	public ModelAndView form(Produto produto) {
		ModelAndView mvn = new ModelAndView("produto/novo");
		mvn.addObject("produto", produto);
		mvn.addObject("comissoes", TabelaComissao.values());
		mvn.addObject("categorias", CategoriaProduto.values());
		mvn.addObject("operadoras", daoOperadora.findAll());
		mvn.addObject("produtos", dao.findAll());
		return mvn;
	}

	@RequestMapping({ "/salvar" })
	public ModelAndView salvar(@Valid Produto produto, BindingResult result) {
		if (result.hasErrors()) {
			return form(produto);
		}

		dao.save(produto);
		return form(new Produto());
	}

	@RequestMapping("/remover/{produto}")
	private ModelAndView remover(@PathVariable("produto") Produto produto) {
		dao.delete(produto);
		return form(produto);
	}

	@RequestMapping("/findAll")
	private ModelAndView lista() {
		ModelAndView mvn = new ModelAndView("produto/produtos");
		mvn.addObject("produtos", dao.findAll());
		return mvn;
	}


	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.setValidator(produtoValidation);

	}
}
