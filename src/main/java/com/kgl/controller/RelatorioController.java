package com.kgl.controller;

import java.math.BigDecimal;
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
		for (Movimentacao obj : list) {
			Relatorio dto = new Relatorio();
			dto.setDataPagamento(dtfOut.print(obj.getDtPagamento()));
			dto.setProposta(obj.getContrato().getCodigoContrato().toString());
			dto.setValorContrato(obj.getFormatarValorContrato());
			dto.setValorCorretor(obj.getFormatarValorCorretor().toString());
			dto.setValorKgl(obj.getFormatarValorKgl());
			dto.setLucro(obj.getFormatarValorKgl());
			dto.setStatus(obj.getStatus().toString());
			dto.setIdCorretor("Verificar ");
			dto.setSegurado(obj.getContrato().getSegurado().getNome().toUpperCase());
			dto.setPorcentagem("Verificar");
			dto.setTaxa(obj.getTaxa().toString());
			dto.setAdmin(obj.getTaxa().toString());
			dto.setDespesaAdmin("");
			dto.setValorLiquido(String.valueOf(obj.getValorCorretor().subtract(obj.getValorKgl())));
			dto.setPorcentagemAdmin("+8,50%");
			dto.setDespesaAdmin(null == obj.getTotalDesconto() ? "" : obj.getTotalDesconto().toString());
			dto.setProduto(obj.getContrato().getProduto().toString());
			if(obj.getContrato().getId().equals(auxId)){
				dto.setParcela(String.valueOf(++parcela));
				auxId = obj.getContrato().getId();
			}else {
				auxId = obj.getContrato().getId();
				parcela = 1;
				dto.setParcela("1");
			}
			
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

		return new ModelAndView(view, params);
	}

}