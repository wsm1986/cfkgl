package com.kgl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.kgl.models.Response;
import com.kgl.models.Role;
import com.kgl.models.User;
import com.kgl.services.CorretorService;
import com.kgl.services.MovimentacaoService;
import com.kgl.services.UsuarioService;
import com.kgl.validator.CorretorValidator;

@Controller
@RequestMapping("/relatorio")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)

public class RelatorioController {

	@Autowired
	MovimentacaoService movimentcaoService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	CorretorService corretorService;

	@RequestMapping({ "/form" })
	public ModelAndView gerarRelatorio(Movimentacao movimentacao, HttpSession session) {
		ModelAndView mvn = new ModelAndView("movimentacao/relatorio");
		mvn.addObject("response", new Response());
		mvn.addObject("corretores", corretorService.todosCorretores());

		return mvn;
	}

	@RequestMapping(value = "/pdf/", method = RequestMethod.POST)
	public ModelAndView imprimirPdf(Response response) {
		JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:relatorioKgl.jrxml");
		view.setApplicationContext(appContext);
		Map<String, Object> params = new HashMap<>();
		
		
		response.setCorretor(null == response.getCorretorRelatorio() ? "" :  response.getCorretorRelatorio().getId().toString());
		
		List<Movimentacao> list = movimentcaoService.buscarMovimentacao(response);
		
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");
		Integer parcela = 0;
		Long auxId = list.get(0).getContrato().getId();
		BigDecimal totalParcela = new BigDecimal(0);
		BigDecimal totalBruto = new BigDecimal(0);

		// Variavel para controlar a proposta
		List<Relatorio> relatorios = new ArrayList<>();
		BigDecimal porcentagem = new BigDecimal(8.5 / 100);
		NumberFormat vlr = NumberFormat.getCurrencyInstance();

		for (Movimentacao obj : list) {
			Relatorio dto = new Relatorio();
			dto.setDataPagamento(dtfOut.print(obj.getDtPagamento()));
			dto.setProposta(obj.getContrato().getCodigoContrato().toString());
			dto.setValorContrato(obj.getFormatarValorContrato());
			dto.setValorKgl(obj.getFormatarValorKgl());
			dto.setLucro(obj.getFormatarValorKgl());
			dto.setStatus(obj.getStatus().toString());
			dto.setIdCorretor("Verificar ");
			dto.setSegurado(obj.getContrato().getSegurado().getNome().toUpperCase());
			dto.setTarifa(obj.getTarifa().toString());
			dto.setAdmin(obj.getTaxa().toString());
			dto.setValorLiquido(String.valueOf(obj.getValorCorretor().subtract(obj.getValorKgl())));
			
			dto.setProduto(obj.getContrato().getProduto().toString());
			if(obj.getContrato().getId().equals(auxId)){
				dto.setParcela(String.valueOf(++parcela));
				auxId = obj.getContrato().getId();
			}else {
				auxId = obj.getContrato().getId();
				parcela = 1;
				dto.setParcela("1");
			}
			dto.setPorcentagemAdmin(obj.getContrato().getProduto().getParcelaCorretor().retornoPorcentagem(parcela).toString());
			
			BigDecimal valorLiquido = 		obj.getContrato().getProduto().getParcelaCorretor().calcularValorPorcentagem(
							obj.getContrato().getValor(), Integer.valueOf(dto.getPorcentagemAdmin()));
			
			
			dto.setValorCorretor(vlr.format(valorLiquido));
			dto.setValorLiquido(obj.getFormatarValorCorretor().toString());

			dto.setDespesaAdmin(vlr.format(obj.getValorCorretor().subtract(valorLiquido)));

			// Somar Valor
			totalBruto= totalBruto.add(obj.getContrato().getValor());
			totalParcela = totalParcela.add(obj.getValorCorretor());
			relatorios.add(dto);
		}

		params.put("datasource", relatorios);
		
		params.put("corretor", response.getCorretorRelatorio().getNome());
		params.put("codigoCorretor", response.getCorretor());
		params.put("banco", list.get(0).getContrato().getCorretor().getConta().getBanco().toString());
		params.put("agencia", list.get(0).getContrato().getCorretor().getConta().getAgencia().toString());
		params.put("conta", list.get(0).getContrato().getCorretor().getConta().getNumero());
		params.put("dtInicio", response.getDtInicial());
		params.put("dtFinal", response.getDtFinal());
		params.put("totalBruto", totalBruto);
		params.put("totalParcela", totalParcela);
		
        InputStream caminhoImagem = getClass().getResourceAsStream("/static/imagens/logo.png");
        InputStream caminhoImagem2 = getClass().getResourceAsStream("/static/imagens/footer-logo.png");

		params.put("logo",caminhoImagem);
		params.put("logo2",caminhoImagem2);

;    

		return new ModelAndView(view, params);
	}

}
