package com.kgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.CategoriaProduto;
import com.kgl.models.Produto;
import com.kgl.models.TabelaComissao;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoRepository dao; 
	
	@Autowired
	OperadoraRepository daoOperadora; 
	
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
}
