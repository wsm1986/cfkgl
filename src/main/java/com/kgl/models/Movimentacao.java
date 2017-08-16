package com.kgl.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal valorCorretor;

	private BigDecimal valorKgl;

	private String porcentagemMov;

	@ManyToOne
	private Contrato contrato;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private DateTime dtPagamento;

	private BigDecimal lucro;
	@Enumerated(EnumType.STRING)
	private StatusMovimentacao status;

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public DateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(DateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public StatusMovimentacao getStatus() {
		return status;
	}

	public void setStatus(StatusMovimentacao status) {
		this.status = status;
	}

	public BigDecimal getValorKgl() {
		return valorKgl;
	}

	public void setValorKgl(BigDecimal valorKgl) {
		this.valorKgl = valorKgl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPorcentagemMov() {
		return porcentagemMov;
	}

	public void setPorcentagemMov(String porcentagemMov) {
		this.porcentagemMov = porcentagemMov;
	}

	public BigDecimal getValorCorretor() {
		return valorCorretor;
	}

	public void setValorCorretor(BigDecimal valorCorretor) {
		this.valorCorretor = valorCorretor;
	}

	public BigDecimal getLucro() {
		return lucro;
	}

	public void setLucro(BigDecimal lucro) {
		this.lucro = lucro;
	}

}
