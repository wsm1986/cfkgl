package com.kgl.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.StatusContrato;
import com.kgl.models.StatusMovimentacao;
import com.kgl.services.ContratoService;
import com.kgl.webservices.MovimentacaoRepository;
import com.kgl.webservices.OperadoraRepository;
import com.kgl.webservices.ProdutoRepository;


@Service
public class CriadorDeContrato {
	
	@Autowired
	ContratoService contratoService;
	
	@Autowired
	MovimentacaoRepository movimentacaoRepository;
	
	@Autowired
	OperadoraRepository daoOperadora; 
	
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public void implantarContrato(Contrato contrato) {
		//List<Produto> movimentacaoEntrada = produtoRepository.findByOperadoraAndTabelaComissao(contrato.getSubProduto().getProduto().getOperadora(), TabelaComissao.COMISSAO_KGL);
		contrato.setStatusContrato(StatusContrato.AGUARDANDO_IMPLANTACAO);
		contratoService.salvar(contrato);
		gerarMovimentacao(contrato);
		//gerarMovimentacaoFinanceiraEntrada(contrato);
		//gerarMovimentacaoFinanceira(contrato);
	}


	/*private void gerarMovimentacaoFinanceira(Contrato contrato) {
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
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagmovem(contrato.getValor(),prod.getParcelaKgl().getDecimaPrimeiraParcelaKgl()), TipoMovimentacao.ENTRADA,10);
		gerarMovimentacao(contrato,prod.getParcelaKgl().calcularValorPorcentagem(contrato.getValor(),prod.getParcelaKgl().getDecimaSegundaParcelaKgl()), TipoMovimentacao.ENTRADA,11);
		
	
}*/
	
	
	private void gerarMovimentacao(Contrato contrato) {
		for (int mesPagamento = 0; mesPagamento < 12; mesPagamento++) {
			Movimentacao mov = new Movimentacao();
			mov.setContrato(contrato);
			mov.setStatus(StatusMovimentacao.AGUARDADO_PAGAMENTO);

			
			if(mesPagamento == 0) {
				mov.setTaxa(new Double(0));
				
			}else {
				mov.setTaxa(new Double(8.5));
			}
			
			//Caso seja a segunda parcela cobrar tarifa
			if(mesPagamento == 1) {
				mov.setTarifa(contrato.getTarifa());
				mov.setValorCorretor(contrato.getProduto().getParcelaCorretor()
					.calcularValorLucro(contrato.getValor(), mesPagamento));
				
			}else {
				mov.setTarifa(new BigDecimal(0));;
				mov.setValorCorretor(contrato.getProduto().getParcelaCorretor()
						.calcularValorLucro(contrato.getValor(), mesPagamento));
			}
			mov.setValorKgl(contrato.getProduto().getParcelaKgl()
					.calcularValorLucro(contrato.getValor(), mesPagamento));
			mov.setLucro(mov.getValorKgl().subtract(mov.getValorCorretor()));
			mov.setDtPagamento(contrato.getDtCadastro().plusMonths(mesPagamento));
			
			if (mov.getValorKgl().compareTo(BigDecimal.ZERO) == 1) {
				movimentacaoRepository.save(mov);
			}
		}

	}

	public CriadorDeContrato() {
		super();
	}

}
