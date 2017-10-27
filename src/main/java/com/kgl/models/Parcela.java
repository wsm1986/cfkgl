package com.kgl.models;

import java.math.BigDecimal;

public interface Parcela  {
	BigDecimal calcularValorPorcentagem(BigDecimal valor, Integer porcentagem);
	BigDecimal calcularValorLucro(BigDecimal valor, Integer porcentagem);

	
}
