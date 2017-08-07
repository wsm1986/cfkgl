package com.kgl.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class ParcelaKgl implements Parcela{

	private Integer totalComissaoKgl;
	
	private Integer primeiraParcelaKgl = 0;
	
	private Integer segundaParcelaKgl = 0;
	
	private Integer terceiraParcelaKgl= 0;
	
	private Integer quartaParcelaKgl= 0;
	
	private Integer quintaParcelaKgl= 0;
	
	private Integer sextaParcelaKgl= 0;
	
	private Integer setimaParcelaKgl= 0;
	
	private Integer oitavaParcelaKgl= 0;
	
	private Integer nonaParcelaKgl= 0;
	
	private Integer decimaParcelaKgl= 0;
	
	private Integer decimaPrimeiraParcelaKgl= 0;
	
	private Integer decimaSegundaParcelaKgl= 0;


	public Integer getTotalComissao() {
		return totalComissaoKgl = primeiraParcelaKgl + segundaParcelaKgl + terceiraParcelaKgl + quartaParcelaKgl + quintaParcelaKgl
				+ sextaParcelaKgl + setimaParcelaKgl + oitavaParcelaKgl + nonaParcelaKgl - decimaParcelaKgl + decimaPrimeiraParcelaKgl
				+ decimaSegundaParcelaKgl;
	}

	public void setTotalComissao(Integer totalComissao) {
		this.totalComissaoKgl = totalComissao;
	}

	public Integer getTotalComissaoKgl() {
		return totalComissaoKgl;
	}

	public void setTotalComissaoKgl(Integer totalComissaoKgl) {
		this.totalComissaoKgl = totalComissaoKgl;
	}

	public Integer getPrimeiraParcelaKgl() {
		return primeiraParcelaKgl;
	}

	public void setPrimeiraParcelaKgl(Integer primeiraParcelaKgl) {
		this.primeiraParcelaKgl = primeiraParcelaKgl;
	}

	public Integer getSegundaParcelaKgl() {
		return segundaParcelaKgl;
	}

	public void setSegundaParcelaKgl(Integer segundaParcelaKgl) {
		this.segundaParcelaKgl = segundaParcelaKgl;
	}

	public Integer getTerceiraParcelaKgl() {
		return terceiraParcelaKgl;
	}

	public void setTerceiraParcelaKgl(Integer terceiraParcelaKgl) {
		this.terceiraParcelaKgl = terceiraParcelaKgl;
	}

	public Integer getQuartaParcelaKgl() {
		return quartaParcelaKgl;
	}

	public void setQuartaParcelaKgl(Integer quartaParcelaKgl) {
		this.quartaParcelaKgl = quartaParcelaKgl;
	}

	public Integer getQuintaParcelaKgl() {
		return quintaParcelaKgl;
	}

	public void setQuintaParcelaKgl(Integer quintaParcelaKgl) {
		this.quintaParcelaKgl = quintaParcelaKgl;
	}

	public Integer getSextaParcelaKgl() {
		return sextaParcelaKgl;
	}

	public void setSextaParcelaKgl(Integer sextaParcelaKgl) {
		this.sextaParcelaKgl = sextaParcelaKgl;
	}

	public Integer getSetimaParcelaKgl() {
		return setimaParcelaKgl;
	}

	public void setSetimaParcelaKgl(Integer setimaParcelaKgl) {
		this.setimaParcelaKgl = setimaParcelaKgl;
	}

	public Integer getOitavaParcelaKgl() {
		return oitavaParcelaKgl;
	}

	public void setOitavaParcelaKgl(Integer oitavaParcelaKgl) {
		this.oitavaParcelaKgl = oitavaParcelaKgl;
	}

	public Integer getNonaParcelaKgl() {
		return nonaParcelaKgl;
	}

	public void setNonaParcelaKgl(Integer nonaParcelaKgl) {
		this.nonaParcelaKgl = nonaParcelaKgl;
	}

	public Integer getDecimaParcelaKgl() {
		return decimaParcelaKgl;
	}

	public void setDecimaParcelaKgl(Integer decimaParcelaKgl) {
		this.decimaParcelaKgl = decimaParcelaKgl;
	}

	public Integer getDecimaPrimeiraParcelaKgl() {
		return decimaPrimeiraParcelaKgl;
	}

	public void setDecimaPrimeiraParcelaKgl(Integer decimaPrimeiraParcelaKgl) {
		this.decimaPrimeiraParcelaKgl = decimaPrimeiraParcelaKgl;
	}

	public Integer getDecimaSegundaParcelaKgl() {
		return decimaSegundaParcelaKgl;
	}

	public void setDecimaSegundaParcelaKgl(Integer decimaSegundaParcelaKgl) {
		this.decimaSegundaParcelaKgl = decimaSegundaParcelaKgl;
	}

	@Override
	public BigDecimal calcularValorPorcentagem(BigDecimal valor, Integer porcentagem) {
			Double calc = Double.valueOf(Double.valueOf(porcentagem) / 100);
			return valor.multiply(new BigDecimal(calc));

	}
}