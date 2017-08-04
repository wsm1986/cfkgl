package com.kgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.CategoriaProduto;
import com.kgl.models.Produto;
import com.kgl.models.SubProduto;
import com.kgl.models.TabelaComissao;
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
	public ModelAndView salvar(Produto produto, BindingResult result) {
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

	@RequestMapping({ "/formSubProduto" })
	public ModelAndView form(SubProduto subProduto) {
		ModelAndView mvn = new ModelAndView("produto/novosubproduto");
		mvn.addObject("produtos", dao.findAll());
		mvn.addObject("subProdutos", daoSubProduto.findAll());
		return mvn;
	}

	@RequestMapping({ "/salvarSubProduto" })
	public ModelAndView salvar(SubProduto subProduto, BindingResult result) {
		if (result.hasErrors()) {
			return form(subProduto);
		}

		daoSubProduto.save(subProduto);
		return form(new SubProduto());
	}
	
	@RequestMapping("/removerSub/{subProduto}")
	private ModelAndView removerSub(@PathVariable("subProduto") SubProduto subProduto) {
		daoSubProduto.delete(subProduto);
		return form(subProduto);
	}
}
