package com.kgl.models;

import java.io.Serializable;

public enum StatusMovimentacao  implements Serializable {
	PAGO,AGUARDADO_PAGAMENTO,RECEBI, RECUSADO;
}