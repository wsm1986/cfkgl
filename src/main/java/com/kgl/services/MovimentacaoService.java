package com.kgl.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;


public interface MovimentacaoService {
	
	List<Movimentacao> findByContrato(Contrato contrato);
	List<Movimentacao> findByContratoCorretorId(Long id);
	List<Movimentacao> findByContratoId(Long id);
	List<Movimentacao> buscarMovimentacao(Response response );
	void salvar(Movimentacao mov);
	void atualizarContrato(Movimentacao mov, HttpSession session, String flag);
	Movimentacao findById(Long id);
	void excluir(Long id);
	List<Movimentacao> buscarMovimentacoes();
	void gerarMovimentacao(Contrato contrato);

	
}
