package com.kgl.models;

import org.hibernate.validator.constraints.NotEmpty;


public class FieldModel {

	@NotEmpty
	private String sessao;
	@NotEmpty
	private String field;
	@NotEmpty
	private String value;
	public String getSessao() {
		return sessao;
	}
	public void setSessao(String sessao) {
		this.sessao = sessao;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
