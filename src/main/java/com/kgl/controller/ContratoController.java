package com.kgl.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Banco;
import com.kgl.models.CategoriaProduto;
import com.kgl.models.Contrato;
import com.kgl.models.Corretor;
import com.kgl.models.Segurado;
import com.kgl.models.TabelaComissao;
import com.kgl.repository.CorretorRepository;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;
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

	@RequestMapping({ "/", "/form" })
	private ModelAndView form(Contrato contrato, Segurado segurado) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		mvn.addObject("corretores", corretorDao.findAll());
		mvn.addObject("subProdutos", produtoDao.findAll());
		mvn.addObject("segurados", "WELL");
		return mvn;
	}
	
	

	@RequestMapping({ "/salvar"})
	private ModelAndView salvar(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		Contrato novo = new Contrato();
		novo.setCodigoContrato(1l);
		novo.setCorretor(corretorDao.findOne(1l));
		novo.setValor(new BigDecimal(1000));
		contratoNovo.implantarContrato(novo);

		
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

	}
	
	
	@RequestMapping("/addSegurado")
	private ModelAndView addSegurado(Segurado segurado) {
		System.out.println(segurado.getNome());
		ModelAndView mvn = new ModelAndView("redirect:/contrato/form");
		return mvn;
	}	
}
