package com.kgl.models;

import java.math.BigDecimal;

public interface Parcela {
	BigDecimal calcularValorPorcentagem(BigDecimal valor, Integer porcentagem);
	
}
