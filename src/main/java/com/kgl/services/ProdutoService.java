package com.kgl.services;

import java.util.List;

import com.kgl.models.Operadora;
import com.kgl.models.Produto;
import com.kgl.enums.TabelaComissao;

public interface ProdutoService {
	
	List<Produto>findByOperadoraAndTabelaComissao(Operadora operadora, TabelaComissao comissao);
	
	List<Produto> produtos();
	
	List<Produto> produtosAtivos();

	
	void salvar(Produto p);
	
	void deletar(Long id);


}
