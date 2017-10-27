package com.kgl.models;

import java.io.Serializable;

public enum Banco  implements Serializable {

	Santander("Santander", "033"), 
	Itau("Itau", "341"), 
	Bradesco("Bradesco", "237"), 
	Caixa("Caixa", "104"), 
	HSBC("HSBC","399"), 
	BancoDoBrasil("Banco Do Brasil", "001");

	private String nome;
	private String codigo;

	private Banco(String nome, String codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
