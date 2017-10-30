package com.kgl.services;

import java.util.List;


import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;


public interface MovimentacaoService {
	
	List<Movimentacao> findByContrato(Contrato contrato);
	List<Movimentacao> findByContratoCorretorId(Long id);
	List<Movimentacao> findByContratoId(Long id);
	List<Movimentacao> buscarMovimentacao(Response response );
	void salvar(Movimentacao mov);
	void excluir(Long id);
	List<Movimentacao> buscarMovimentacoes();

	
}
