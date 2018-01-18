package com.kgl.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.kgl.enums.StatusMovimentacao;

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
	@CreatedDate
	private DateTime dtPagamento;

	private BigDecimal lucro;
	
	@Enumerated(EnumType.STRING)
	private StatusMovimentacao status;

	private BigDecimal tarifa;

	private Double taxa;

	private BigDecimal totalDesconto;

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
		BigDecimal porcentagem = new BigDecimal(getTaxa() / 100);
		BigDecimal desconto = valorKgl.multiply(porcentagem);
		this.valorKgl = valorKgl.subtract(desconto);
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
		BigDecimal porcentagem = new BigDecimal(getTaxa() / 100);
		BigDecimal desconto = valorCorretor.multiply(porcentagem);
		this.valorCorretor = valorCorretor.subtract(desconto).subtract(getTarifa());
	}

	public BigDecimal getLucro() {
		return lucro;
	}

	public void setLucro(BigDecimal lucro) {
		this.lucro = lucro;
	}

	public Double getTaxa() {
		return taxa;
	}

	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}

	public BigDecimal getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(BigDecimal totalDesconto) {
		this.totalDesconto = new BigDecimal(taxa).add(tarifa);
	}

	public BigDecimal getTarifa() {
		return tarifa == null ? new BigDecimal("0") : tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	public String getDtConverter() {
		return String.valueOf(dtPagamento.getMonthOfYear() + "/" + dtPagamento.getYear());
	}

	public String getFormatarValorKgl() {
		NumberFormat vlr = NumberFormat.getCurrencyInstance();
		return vlr.format(getValorKgl());
	}

	public String getFormatarValorContrato() {
		NumberFormat vlr = NumberFormat.getCurrencyInstance();
		return vlr.format(getContrato().getValor());
	}

	public String getFormatarValorCorretor() {
		NumberFormat vlr = NumberFormat.getCurrencyInstance();
		return vlr.format(getValorCorretor());
	}

	public String getFormatarValorLucro() {
		NumberFormat vlr = NumberFormat.getCurrencyInstance();
		return vlr.format(getLucro());
	}

}
