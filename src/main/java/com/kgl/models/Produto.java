package com.kgl.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Produto implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer idadeMinima;

	private Integer idadeMaxima;

	@Enumerated(EnumType.STRING)
	private TabelaComissao tabelaComissao;

	@Embedded 
	private ParcelaCorretor parcelaCorretor;

	@Embedded
	private ParcelaKgl parcelaKgl;

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

	public String getDescricao() {
		return operadora.getNome() + " Idade:  " + idadeMinima + " a " + idadeMaxima + " - " + categoriaProduto.name()
				+ " - TABELA DE COMISSAO: " + tabelaComissao.getNome();
	}


	public ParcelaKgl getParcelaKgl() {
		return parcelaKgl;
	}

	public void setParcelaKgl(ParcelaKgl parcelaKgl) {
		this.parcelaKgl = parcelaKgl;
	}

	public ParcelaCorretor getParcelaCorretor() {
		return parcelaCorretor;
	}

	public void setParcelaCorretor(ParcelaCorretor parcelaCorretor) {
		this.parcelaCorretor = parcelaCorretor;
	}

	@Override
	public String toString() {
		return operadora.getNome();
	}

	

}
