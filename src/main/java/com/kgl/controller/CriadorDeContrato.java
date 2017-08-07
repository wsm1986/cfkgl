package com.kgl.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Parcela;
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
		//List<Produto> movimentacaoEntrada = produtoRepository.findByOperadoraAndTabelaComissao(contrato.getSubProduto().getProduto().getOperadora(), TabelaComissao.COMISSAO_KGL);
		contratoRepository.save(contrato);
		gerarMovimentacaoFinanceiraEntrada(contrato);
		gerarMovimentacaoFinanceira(contrato);
	}


	private void gerarMovimentacaoFinanceira(Contrato contrato) {
			Produto prod = contrato.getSubProduto().getProduto();
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getPrimeiraParcela()),TipoMovimentacao.SAIDA, 0);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getSegundaParcela()), TipoMovimentacao.SAIDA,1);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getTerceiraParcela()),TipoMovimentacao.SAIDA, 2);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getQuartaParcela()), TipoMovimentacao.SAIDA,3);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getQuintaParcela()), TipoMovimentacao.SAIDA,4);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getSextaParcela()), TipoMovimentacao.SAIDA,5);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getSetimaParcela()),TipoMovimentacao.SAIDA, 6);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getOitavaParcela()), TipoMovimentacao.SAIDA,7);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getNonaParcela()),TipoMovimentacao.SAIDA, 8);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getDecimaParcela()), TipoMovimentacao.SAIDA,9);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getDecimaPrimeiraParcela()), TipoMovimentacao.SAIDA,10);
			gerarMovimentacao(contrato,prod.getParcelaCorretor().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaCorretor().getDecimaSegundaParcela()), TipoMovimentacao.SAIDA,11);
			
		
	}
	
	private void gerarMovimentacaoFinanceiraEntrada(Contrato contrato) {
		Produto prod = contrato.getSubProduto().getProduto();
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getPrimeiraParcelaKgl()),TipoMovimentacao.ENTRADA, 0);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getSegundaParcelaKgl()), TipoMovimentacao.ENTRADA,1);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getTerceiraParcelaKgl()),TipoMovimentacao.ENTRADA, 2);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getQuartaParcelaKgl()), TipoMovimentacao.ENTRADA,3);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getQuintaParcelaKgl()), TipoMovimentacao.ENTRADA,4);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getSextaParcelaKgl()), TipoMovimentacao.ENTRADA,5);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getSetimaParcelaKgl()),TipoMovimentacao.ENTRADA, 6);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getOitavaParcelaKgl()), TipoMovimentacao.ENTRADA,7);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getNonaParcelaKgl()),TipoMovimentacao.ENTRADA, 8);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getDecimaParcelaKgl()), TipoMovimentacao.ENTRADA,9);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getDecimaPrimeiraParcelaKgl()), TipoMovimentacao.ENTRADA,10);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getDecimaSegundaParcelaKgl()), TipoMovimentacao.ENTRADA,11);
		
	
}
	
	
	private void gerarMovimentacao(Contrato contrato, BigDecimal valor, TipoMovimentacao tipo, Integer mesPagamento) {
		Movimentacao mov = new Movimentacao();
		mov.setDescricao(contrato.toString());
		mov.setTipoMovimentacao(tipo);
		mov.setValor(valor);
		mov.setContrato(contrato);
		mov.setDtPagamento(contrato.getDtCadastro().plusMonths(mesPagamento));
		if (valor.compareTo(BigDecimal.ZERO) == 1){
			movimentacaoRepository.save(mov);
		}
		
	}

	public CriadorDeContrato() {
		super();
	}

}