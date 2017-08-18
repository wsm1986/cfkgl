package com.kgl.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Role;
import com.kgl.models.Segurado;
import com.kgl.models.StatusContrato;
import com.kgl.repository.CorretorRepository;
import com.kgl.services.HomeBean;
import com.kgl.validator.ContratoValidator;
import com.kgl.webservices.ContratoRepository;
import com.kgl.webservices.MovimentacaoRepository;
import com.kgl.webservices.SubProdutoRepository;

@Controller
@RequestMapping("/contrato")
public class ContratoKglController {

	@Autowired
	private CorretorRepository corretorDao;

	@Autowired
	private SubProdutoRepository produtoDao;

	@Autowired
	CriadorDeContrato contratoNovo;

	@Autowired
	ContratoRepository contratoRepository;

	@Autowired
	MovimentacaoRepository movRepository;

	@Autowired
	CorretorRepository corretorRepository;
	
	@Autowired
	private ContratoValidator contratoValidation;
	
	@Autowired
	private HomeBean home;
	
	@RequestMapping({ "/", "/form" })
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("contrato/novo");
		mvn.addObject("corretores", corretorDao.findAll());
		mvn.addObject("subProdutos", produtoDao.findAll());
		return mvn;

	}

	@RequestMapping({ "/listar" })
	private ModelAndView listar() {
		ModelAndView mvn = new ModelAndView("contrato/listar");
		/*com.kgl.models.User user = (com.kgl.models.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for (Role role : user.getRoles()) {
			if(role.getAuthority().equals("ROLE_ADMIN")) {
				mvn.addObject("contratos", contratoRepository.findAll());
			}else {
				mvn.addObject("contratos", contratoRepository.findByCorretor(corretorRepository.findByEmail(user.getUsername())));
			}
			System.out.println(role.getAuthority());
		}*/
		
		if(home.permissaoUsuario()) {
			mvn.addObject("contratos", contratoRepository.findAll());
		}else {
			String email = home.emailLogado();
			mvn.addObject("contratos", contratoRepository.findByCorretor(corretorRepository.findByEmail(email)));
		}
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

	@RequestMapping(value = "/detalharContr/{id}", method = RequestMethod.GET)
	public ModelAndView detalharContr(@PathVariable("id") Long id) {
		ModelAndView mvn = new ModelAndView("contrato/listar");
		mvn.addObject("contratos", contratoRepository.findAll());
		mvn.addObject("movimentacoes", movRepository.findByContrato(contratoRepository.findOne(id)));
		return mvn;
	}

	@RequestMapping(value = "/remover/{contrato}", method = RequestMethod.GET)
	public ModelAndView remover(@PathVariable("contrato") Contrato contrato) {
		contratoRepository.delete(contrato);
		return listar();
	}
	
	@RequestMapping(value = "/update/recusar/{contrato}", method = RequestMethod.GET)
	public ModelAndView recusar(@PathVariable("contrato") Contrato contrato) {
		contrato.setStatusContrato(StatusContrato.RECUSADO);
		contratoRepository.save(contrato);
		return listar();
	}
	
	@RequestMapping(value = "/update/implantar/{contrato}", method = RequestMethod.GET)
	public ModelAndView implantar(@PathVariable("contrato") Contrato contrato) {
		contrato.setStatusContrato(StatusContrato.IMPLANTADO);
		contratoRepository.save(contrato);
		return listar();
	}

}
