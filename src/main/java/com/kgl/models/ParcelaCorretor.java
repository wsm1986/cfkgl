package com.kgl.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class ParcelaCorretor implements Parcela{

	private Integer totalComissao;
	
	private Integer primeiraParcela = 0;
	
	private Integer segundaParcela = 0;
	
	private Integer terceiraParcela= 0;
	
	private Integer quartaParcela= 0;
	
	private Integer quintaParcela= 0;
	
	private Integer sextaParcela= 0;
	
	private Integer setimaParcela= 0;
	
	private Integer oitavaParcela= 0;
	
	private Integer nonaParcela= 0;
	
	private Integer decimaParcela= 0;
	
	private Integer decimaPrimeiraParcela= 0;
	
	private Integer decimaSegundaParcela= 0;


	public Integer getTotalComissao() {
		return totalComissao = primeiraParcela + segundaParcela + terceiraParcela + quartaParcela + quintaParcela
				+ sextaParcela + setimaParcela + oitavaParcela + nonaParcela - decimaParcela + decimaPrimeiraParcela
				+ decimaSegundaParcela;
	}

	public void setTotalComissao(Integer totalComissao) {
		this.totalComissao = totalComissao;
	}

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
}
