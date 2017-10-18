package com.kgl.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
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

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private Segurado segurado;

	@OneToOne
	private Corretor corretor;

	@NotNull(message = "valor é obrigatório")
	private BigDecimal valor;

	private BigDecimal tarifa;

	@OneToOne
	private Produto Produto;

	@Enumerated(EnumType.STRING)
	private StatusContrato statusContrato;
	

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

	public Contrato() {
	}

	public Segurado getSegurado() {
		return segurado;
	}

	public void setSegurado(Segurado segurado) {
		this.segurado = segurado;
	}

	public StatusContrato getStatusContrato() {
		return statusContrato;
	}

	public void setStatusContrato(StatusContrato statusContrato) {
		this.statusContrato = statusContrato;
	}

	

	public Produto getProduto() {
		return Produto;
	}

	public void setProduto(Produto produto) {
		Produto = produto;
	}

	public BigDecimal getTarifa() {
		return tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}


}
