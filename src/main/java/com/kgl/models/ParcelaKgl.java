package com.kgl.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class ParcelaKgl implements Parcela ,Serializable{

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



	public Integer getTotalComissaoKgl() {
		return totalComissaoKgl;
	}

	public void setTotalComissaoKgl(Integer totalComissaoKgl) {
		this.totalComissaoKgl = primeiraParcelaKgl + segundaParcelaKgl + terceiraParcelaKgl + quartaParcelaKgl + quintaParcelaKgl
				+ sextaParcelaKgl + setimaParcelaKgl + oitavaParcelaKgl + nonaParcelaKgl - decimaParcelaKgl + decimaPrimeiraParcelaKgl
				+ decimaSegundaParcelaKgl;
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

	@Override
	public BigDecimal calcularValorLucro(BigDecimal valor, Integer numParcela) {
		switch (numParcela) {
		case 0:
			numParcela = this.getPrimeiraParcelaKgl();
			break;

		case 1:
			numParcela = this.getSegundaParcelaKgl();
			break;

		case 2:
			numParcela = this.getTerceiraParcelaKgl();
			break;

		case 3:
			numParcela = this.getQuartaParcelaKgl();
			break;

		case 4:
			numParcela = this.getQuintaParcelaKgl();
			break;

		case 5:
			numParcela = this.getSextaParcelaKgl();
			break;

		case 6:
			numParcela = this.getSetimaParcelaKgl();
			break;

		case 7:
			numParcela = this.getOitavaParcelaKgl();
			break;

		case 8:
			numParcela = this.getNonaParcelaKgl();
			break;

		case 9:
			numParcela = this.getDecimaParcelaKgl();
			break;

		case 10:
			numParcela = this.getDecimaPrimeiraParcelaKgl();
			break;

		case 11:
			numParcela = this.getDecimaSegundaParcelaKgl();
			break;

		default:
			return new BigDecimal(0);
		}
		Double calc = Double.valueOf(Double.valueOf(numParcela) / 100);
		return valor.multiply(new BigDecimal(calc));

	}
}