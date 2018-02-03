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

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.kgl.enums.TipoPesquisaMovimentacao;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dtInicial;
	private String dtFinal;
	private String corretor;
	private Corretor corretorRelatorio;

	public String getDtInicial() {
		return dtInicial;
	}

	public void setDtInicial(String dtInicial) {
		this.dtInicial = dtInicial;
	}

	public String getDtFinal() {
		return dtFinal;
	}

	public void setDtFinal(String dtFinal) {
		this.dtFinal = dtFinal;
	}

	public String getCorretor() {
		return ("-1").equals(corretor) || ("undefined").equals(corretor) ? null : corretor;
	}

	public void setCorretor(String corretor) {
		this.corretor = corretor;
	}
	public TipoPesquisaMovimentacao tipoPesquisa() {
		if(StringUtils.isBlank(getDtInicial()) && StringUtils.isBlank(getDtFinal()) && StringUtils.isBlank(getCorretor())) {
			return TipoPesquisaMovimentacao.TODAS;
		}else if(StringUtils.isNotBlank(getDtInicial()) && StringUtils.isBlank(getDtFinal()) && StringUtils.isBlank(getCorretor())) {
			return TipoPesquisaMovimentacao.APARTIR;
		}else if(StringUtils.isNotBlank(getDtInicial()) && StringUtils.isNotBlank(getDtFinal()) && StringUtils.isBlank(getCorretor())) {
			return TipoPesquisaMovimentacao.ENTRE;
		}else if(StringUtils.isBlank(getDtInicial()) && StringUtils.isBlank(getDtFinal()) && StringUtils.isNotBlank(getCorretor())) {
			return TipoPesquisaMovimentacao.CORRETOR;
		}else if(StringUtils.isNotBlank(getDtInicial()) && StringUtils.isBlank(getDtFinal()) && StringUtils.isNotBlank(getCorretor())) {
			return TipoPesquisaMovimentacao.CORRETOR_APARTIR;
		}
		else {
			return TipoPesquisaMovimentacao.FILTRO_COMPLETO;

		}
	}

	public Corretor getCorretorRelatorio() {
		return corretorRelatorio;
	}

	public void setCorretorRelatorio(Corretor corretorRelatorio) {
		this.corretorRelatorio = corretorRelatorio;
	}
}
