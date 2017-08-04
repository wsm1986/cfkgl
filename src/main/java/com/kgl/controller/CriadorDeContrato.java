package com.kgl.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Produto;
import com.kgl.models.SubProduto;
import com.kgl.models.TabelaComissao;
import com.kgl.models.TipoMovimentacao;
import com.kgl.webservices.ContratoRepository;
import com.kgl.webservices.MovimentacaoRepository;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;


@Service
public class CriadorDeContrato {
	
	@Autowired
	ContratoRepository contratoRepository;
	
	@Autowired
	MovimentacaoRepository movimentacaoRepository;
	
	@Autowired
	OperadoraRepository daoOperadora; 
	
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public void implantarContrato(Contrato contrato) {
		List<Produto> movimentacaoEntrada = produtoRepository.findByOperadoraAndTabelaComissao(contrato.getSubProduto().getProduto().getOperadora(), TabelaComissao.COMISSAO_KGL);
		contratoRepository.save(contrato);
		gerarMovimentacaoFinanceira(contrato, movimentacaoEntrada.get(0), TipoMovimentacao.ENTRADA);
		gerarMovimentacaoFinanceira(contrato, contrato.getSubProduto().getProduto(), TipoMovimentacao.SAIDA);
		
	}


	private void gerarMovimentacaoFinanceira(Contrato contrato, Produto prod, TipoMovimentacao tipo) {
		if(!StringUtils.isEmpty(prod.getPrimeiraParcela().toString())){
			gerarMovimentacao(contrato,calcularValorPorcentagem(contrato.getValor(),prod.getPrimeiraParcela()), tipo);
		} 
		if(!StringUtils.isEmpty(prod.getSegundaParcela().toString())){
			gerarMovimentacao(contrato,calcularValorPorcentagem(contrato.getValor(),prod.getSegundaParcela()), tipo);
		} 
		if(!StringUtils.isEmpty(prod.getTerceiraParcela().toString())){
			gerarMovimentacao(contrato,calcularValorPorcentagem(contrato.getValor(),prod.getTerceiraParcela()), tipo);
		} 

		
	}
	
	private void gerarMovimentacao(Contrato contrato, BigDecimal valor, TipoMovimentacao tipo) {
		Movimentacao mov = new Movimentacao();
		mov.setDescricao(contrato.getCodigoContrato() + " " + contrato.getSubProduto().getProduto());
		mov.setTipoMovimentacao(tipo);
		mov.setValor(valor);
		mov.setContrato(contrato);
		movimentacaoRepository.save(mov);
		
	}
	private BigDecimal calcularValorPorcentagem(BigDecimal valor, Integer porcentagem) {
		Double calc = Double.valueOf(Double.valueOf(porcentagem) / 100);
		return valor.multiply(new BigDecimal(calc));
	}

	public CriadorDeContrato() {
		super();
		// TODO Auto-generated constructor stub
	}

}
