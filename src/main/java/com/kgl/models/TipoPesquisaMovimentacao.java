package com.kgl.models;

public enum TipoPesquisaMovimentacao {

	TODAS("0"),APARTIR("1"), ENTRE("2"), CORRETOR("3");
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	private TipoPesquisaMovimentacao(String nome) {
		this.nome = nome;
	}

}
