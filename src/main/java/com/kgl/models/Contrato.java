package com.kgl.models;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long codigoContrato;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Implantação é obrigatório")
	private Calendar dtImplantacao;
	
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data Inicio é obrigatório")
	private Calendar dtInicio;
	
	@OneToOne
	private Corretor corretor;
	

	@OneToOne
	private Operadora operadora;


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


	public Calendar getDtImplantacao() {
		return dtImplantacao;
	}


	public void setDtImplantacao(Calendar dtImplantacao) {
		this.dtImplantacao = dtImplantacao;
	}


	public Calendar getDtInicio() {
		return dtInicio;
	}


	public void setDtInicio(Calendar dtInicio) {
		this.dtInicio = dtInicio;
	}


	public Corretor getCorretor() {
		return corretor;
	}


	public void setCorretor(Corretor corretor) {
		this.corretor = corretor;
	}


	public Operadora getOperadora() {
		return operadora;
	}


	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}

	

}
