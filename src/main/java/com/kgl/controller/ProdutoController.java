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
import com.kgl.models.ParcelaCorretor;
import com.kgl.models.ParcelaKgl;
import com.kgl.models.Produto;
import com.kgl.models.TabelaComissao;
import com.kgl.services.OperadoraService;
import com.kgl.services.ProdutoService;
import com.kgl.validator.ProdutoValidator;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;
import com.kgl.webservices.SubProdutoRepository;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;

	@Autowired
	OperadoraService operadoraService;

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
		mvn.addObject("operadoras", operadoraService.getAllOperadoras());
		mvn.addObject("produtos", produtoService.produtos());
		return mvn;
	}

	@RequestMapping({ "/salvar" })
	public ModelAndView salvar(@Valid Produto produto, BindingResult result) {
		if (result.hasErrors()) {
			return form(produto);
		}
		produto.getParcelaCorretor().setTotalComissaoCorretor(somarComissao(produto.getParcelaCorretor()));
		produto.getParcelaKgl().setTotalComissaoKgl(somarComissaoKgl(produto.getParcelaKgl()));

		produtoService.salvar(produto);
		ModelAndView mvn = new ModelAndView("index");
return mvn;
		//return  lista();// form(new Produto());
	}

	@RequestMapping("/remover/{produto}")
	private ModelAndView remover(@PathVariable("produto") Produto produto) {
		produtoService.deletar(produto.getId());
		return form(produto);
	}

	@RequestMapping("/findAll")
	private ModelAndView lista() {
		ModelAndView mvn = new ModelAndView("produto/produtos");
		mvn.addObject("produtos", produtoService.produtos());
		return mvn;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.setValidator(produtoValidation);

	}

	public Integer somarComissao(ParcelaCorretor p) {
		return p.getPrimeiraParcela() + p.getSegundaParcela() + p.getTerceiraParcela() + p.getQuartaParcela()
				+ p.getQuintaParcela() + p.getSextaParcela() + p.getSetimaParcela() + p.getOitavaParcela()
				+ p.getNonaParcela() + p.getDecimaParcela() + p.getDecimaPrimeiraParcela()
				+ p.getDecimaSegundaParcela();
	}

	public Integer somarComissaoKgl(ParcelaKgl p) {
		return p.getPrimeiraParcelaKgl() + p.getSegundaParcelaKgl() + p.getTerceiraParcelaKgl()
				+ p.getQuartaParcelaKgl() + p.getQuintaParcelaKgl() + p.getSextaParcelaKgl() + p.getSetimaParcelaKgl()
				+ p.getOitavaParcelaKgl() + p.getNonaParcelaKgl() + p.getDecimaParcelaKgl()
				+ p.getDecimaPrimeiraParcelaKgl() + p.getDecimaSegundaParcelaKgl();
	}
}
