package com.kgl.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long codigoContrato;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Cadastro é obrigatório")
	private DateTime dtCadastro;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Inicio é obrigatório")
	private DateTime dtAssinatura;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Inicio é obrigatório")
	private DateTime dtVigencia;

	@OneToMany
	private List<Segurado> segurados = new ArrayList<>();

	@OneToOne
	private Corretor corretor;

	@NotNull(message = "valor é obrigatório")
	private BigDecimal valor;

	@OneToOne
	private SubProduto subProduto;

	private Boolean statusContrato;

	@javax.persistence.Transient
	private String nomeSegurado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigoContrato() {
		return codigoContrato;
	}

	public void setCodigoContrato(Long codigoContrato) {
		this.codigoContrato = codigoContrato;
	}

	public DateTime getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(DateTime dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public DateTime getDtAssinatura() {
		return dtAssinatura;
	}

	public void setDtAssinatura(DateTime dtAssinatura) {
		this.dtAssinatura = dtAssinatura;
	}

	public DateTime getDtVigencia() {
		return dtVigencia;
	}

	public void setDtVigencia(DateTime dtVigencia) {
		this.dtVigencia = dtVigencia;
	}

	public List<Segurado> getSegurados() {
		return segurados;
	}

	public void setSegurados(List<Segurado> segurados) {
		this.segurados = segurados;
	}

	public Corretor getCorretor() {
		return corretor;
	}

	public void setCorretor(Corretor corretor) {
		this.corretor = corretor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public SubProduto getSubProduto() {
		return subProduto;
	}

	public void setSubProduto(SubProduto subProduto) {
		this.subProduto = subProduto;
	}

	public Boolean getStatusContrato() {
		return statusContrato;
	}

	public void setStatusContrato(Boolean statusContrato) {
		this.statusContrato = statusContrato;
	}

	public String getNomeSegurado() {
		return nomeSegurado;
	}

	public void setNomeSegurado(String nomeSegurado) {
		this.nomeSegurado = nomeSegurado;
	}



	

}
