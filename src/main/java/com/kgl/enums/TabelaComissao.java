package com.kgl.enums;

public enum TabelaComissao {

	COMISSAO_A("A"),COMISSAO_B("B"), INTERNO_A("IA");
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	private TabelaComissao(String nome) {
		this.nome = nome;
	}

}
