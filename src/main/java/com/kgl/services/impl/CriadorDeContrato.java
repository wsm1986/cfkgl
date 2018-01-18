package com.kgl.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgl.models.Contrato;
import com.kgl.enums.StatusContrato;
import com.kgl.services.ContratoService;
import com.kgl.services.MovimentacaoService;

@Service
public class CriadorDeContrato {

	@Autowired
	ContratoService contratoService;

	@Autowired
	MovimentacaoService movimentacaoService;

	public void implantarContrato(Contrato contrato) {
		contrato.setStatusContrato(StatusContrato.AGUARDANDO_IMPLANTACAO);
		contratoService.salvar(contrato);
		movimentacaoService.gerarMovimentacao(contrato);
	}

	public CriadorDeContrato() {
		super();
	}

}
