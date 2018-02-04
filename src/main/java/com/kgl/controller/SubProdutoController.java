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

import com.kgl.models.SubProduto;
import com.kgl.services.OperadoraService;
import com.kgl.validator.SubProdutoValidator;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;
import com.kgl.webservices.SubProdutoRepository;

@Controller
@RequestMapping("/subProduto")
public class SubProdutoController {

	@Autowired
	ProdutoRepository dao;

	@Autowired
	SubProdutoRepository daoSubProduto;
	

	@Autowired
	private SubProdutoValidator subProdutoValidation;

	@RequestMapping({ "/form" })
	public ModelAndView form(SubProduto subProduto) {
		ModelAndView mvn = new ModelAndView("produto/novosubproduto");
		mvn.addObject("produtos", dao.findAll());
		mvn.addObject("subProdutos", daoSubProduto.findAll());
		return mvn;
	}

	@RequestMapping({ "/salvar" })
	public ModelAndView salvar(@Valid SubProduto subProduto, BindingResult result) {
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

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.setValidator(subProdutoValidation);

	}
}
