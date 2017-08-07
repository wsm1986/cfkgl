package com.kgl.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Segurado;
import com.kgl.repository.CorretorRepository;
import com.kgl.validator.ContratoValidator;
import com.kgl.webservices.SubProdutoRepository;

@Controller
@RequestMapping("/contrato")
public class ContratoController {

	@Autowired
	private CorretorRepository corretorDao;

	@Autowired
	private SubProdutoRepository produtoDao;

	@Autowired
	CriadorDeContrato contratoNovo;
	
	@Autowired
	private ContratoValidator contratoValidation;

	@RequestMapping({ "/", "/form" })
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		mvn.addObject("corretores", corretorDao.findAll());
		mvn.addObject("subProdutos", produtoDao.findAll());
		return mvn;

	}

	@RequestMapping({ "/salvar" })
	private ModelAndView salvar(@Valid Contrato contrato, BindingResult result) {
		if (result.hasErrors()) {
			return form(contrato);
		}
		ModelAndView mvn = new ModelAndView("contrato/novo");
		contratoNovo.implantarContrato(contrato);

		return mvn;
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
}
