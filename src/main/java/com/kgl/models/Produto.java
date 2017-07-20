package com.kgl.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer idadeMinima;

	private Integer idadeMaxima;

	@Enumerated(EnumType.STRING)
	private TabelaComissao tabelaComissao;

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

	@OneToOne
	private Operadora operadora;

	@Enumerated(EnumType.STRING)
	private CategoriaProduto categoriaProduto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalComissao() {
		return totalComissao = primeiraParcela + segundaParcela + terceiraParcela + quartaParcela + quintaParcela
				+ sextaParcela + setimaParcela + oitavaParcela + nonaParcela - decimaParcela + decimaPrimeiraParcela
				+ decimaSegundaParcela;
	}

	public void setTotalComissao(Integer totalComissao) {
		this.totalComissao = totalComissao;
	}

	public TabelaComissao getTabelaComissao() {
		return tabelaComissao;
	}

	public void setTabelaComissao(TabelaComissao tabelaComissao) {
		this.tabelaComissao = tabelaComissao;
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public Operadora getOperadora() {
		return operadora;
	}

	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}

	public Integer getIdadeMinima() {
		return idadeMinima;
	}

	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}

	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}

	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}

	public Long getIdOperadora() {
		return new Operadora().getId();
	}

	public void setIdOperadora(Long idOperadora) {
		this.setOperadora(new Operadora(idOperadora));
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
}
