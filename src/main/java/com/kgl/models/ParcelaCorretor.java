package com.kgl.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class ParcelaCorretor implements Parcela, Serializable {


	private Integer primeiraParcela = 0;

	private Integer segundaParcela = 0;

	private Integer terceiraParcela = 0;

	private Integer quartaParcela = 0;

	private Integer quintaParcela = 0;

	private Integer sextaParcela = 0;

	private Integer setimaParcela = 0;

	private Integer oitavaParcela = 0;

	private Integer nonaParcela = 0;

	private Integer decimaParcela = 0;

	private Integer decimaPrimeiraParcela = 0;

	private Integer decimaSegundaParcela = 0;

	private Integer totalComissaoCorretor = 0;

	public Integer getPrimeiraParcela() {
		return primeiraParcela;
	}

	public void setPrimeiraParcela(Integer primeiraParcela) {
		this.primeiraParcela = primeiraParcela;
	}

	public Integer getSegundaParcela() {
		return segundaParcela;
	}

	public void setSegundaParcela(Integer segundaParcela) {
		this.segundaParcela = segundaParcela;
	}

	public Integer getTerceiraParcela() {
		return terceiraParcela;
	}

	public void setTerceiraParcela(Integer terceiraParcela) {
		this.terceiraParcela = terceiraParcela;
	}

	public Integer getQuartaParcela() {
		return quartaParcela;
	}

	public void setQuartaParcela(Integer quartaParcela) {
		this.quartaParcela = quartaParcela;
	}

	public Integer getQuintaParcela() {
		return quintaParcela;
	}

	public void setQuintaParcela(Integer quintaParcela) {
		this.quintaParcela = quintaParcela;
	}

	public Integer getSextaParcela() {
		return sextaParcela;
	}

	public void setSextaParcela(Integer sextaParcela) {
		this.sextaParcela = sextaParcela;
	}

	public Integer getSetimaParcela() {
		return setimaParcela;
	}

	public void setSetimaParcela(Integer setimaParcela) {
		this.setimaParcela = setimaParcela;
	}

	public Integer getOitavaParcela() {
		return oitavaParcela;
	}

	public void setOitavaParcela(Integer oitavaParcela) {
		this.oitavaParcela = oitavaParcela;
	}

	public Integer getNonaParcela() {
		return nonaParcela;
	}

	public void setNonaParcela(Integer nonaParcela) {
		this.nonaParcela = nonaParcela;
	}

	public Integer getDecimaParcela() {
		return decimaParcela;
	}

	public void setDecimaParcela(Integer decimaParcela) {
		this.decimaParcela = decimaParcela;
	}

	public Integer getDecimaPrimeiraParcela() {
		return decimaPrimeiraParcela;
	}

	public void setDecimaPrimeiraParcela(Integer decimaPrimeiraParcela) {
		this.decimaPrimeiraParcela = decimaPrimeiraParcela;
	}

	public Integer getDecimaSegundaParcela() {
		return decimaSegundaParcela;
	}

	public void setDecimaSegundaParcela(Integer decimaSegundaParcela) {
		this.decimaSegundaParcela = decimaSegundaParcela;
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
			numParcela = this.getPrimeiraParcela();
			break;

		case 1:
			numParcela = this.getSegundaParcela();
			break;

		case 2:
			numParcela = this.getTerceiraParcela();
			break;

		case 3:
			numParcela = this.getQuartaParcela();
			break;

		case 4:
			numParcela = this.getQuintaParcela();
			break;

		case 5:
			numParcela = this.getSextaParcela();
			break;

		case 6:
			numParcela = this.getSetimaParcela();
			break;

		case 7:
			numParcela = this.getOitavaParcela();
			break;

		case 8:
			numParcela = this.getNonaParcela();
			break;

		case 9:
			numParcela = this.getDecimaParcela();
			break;

		case 10:
			numParcela = this.getDecimaPrimeiraParcela();
			break;

		case 11:
			numParcela = this.getDecimaSegundaParcela();
			break;

		default:
			return new BigDecimal(0);
		}
		Double calc = Double.valueOf(Double.valueOf(numParcela) / 100);
		return valor.multiply(new BigDecimal(calc));

	}

	
	public Integer retornoPorcentagem( Integer numParcela) {
		numParcela = --numParcela;
		switch (numParcela) {
		case 0:
			numParcela = this.getPrimeiraParcela();
			break;

		case 1:
			numParcela = this.getSegundaParcela();
			break;

		case 2:
			numParcela = this.getTerceiraParcela();
			break;

		case 3:
			numParcela = this.getQuartaParcela();
			break;

		case 4:
			numParcela = this.getQuintaParcela();
			break;

		case 5:
			numParcela = this.getSextaParcela();
			break;

		case 6:
			numParcela = this.getSetimaParcela();
			break;

		case 7:
			numParcela = this.getOitavaParcela();
			break;

		case 8:
			numParcela = this.getNonaParcela();
			break;

		case 9:
			numParcela = this.getDecimaParcela();
			break;

		case 10:
			numParcela = this.getDecimaPrimeiraParcela();
			break;

		case 11:
			numParcela = this.getDecimaSegundaParcela();
			break;

		default:
			return 0;
		}
		return numParcela;

	}
	public Integer getTotalComissaoCorretor() {
		return totalComissaoCorretor;
	}

	public void setTotalComissaoCorretor(Integer totalComissaoCorretor) {
		this.totalComissaoCorretor = totalComissaoCorretor;
	}
}
