package com.kgl.enums;

import java.io.Serializable;

public enum StatusMovimentacao  implements Serializable {
	PAGO,AGUARDADO_PAGAMENTO,RECEBI, RECUSADO, PARCIAL;
}