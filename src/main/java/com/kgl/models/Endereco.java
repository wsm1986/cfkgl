package com.kgl.models;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

	
	private String rua;
	private String cep;

	private String numero;

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
