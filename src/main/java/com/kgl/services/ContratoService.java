package com.kgl.services;

import java.util.List;

import com.kgl.models.Contrato;

public interface ContratoService {


	List<Contrato> buscarContrato();

	Contrato buscarContrato(Long id);

	public void salvar(Contrato contrato);

	public void excluir(Long id);

}
