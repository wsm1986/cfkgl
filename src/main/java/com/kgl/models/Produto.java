package com.kgl.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private TabelaComissao comissao;
	
	private Integer totalComissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TabelaComissao getComissao() {
		return comissao;
	}

	public void setComissao(TabelaComissao comissao) {
		this.comissao = comissao;
	}

	public Integer getTotalComissao() {
		return totalComissao;
	}

	public void setTotalComissao(Integer totalComissao) {
		this.totalComissao = totalComissao;
	}
}
