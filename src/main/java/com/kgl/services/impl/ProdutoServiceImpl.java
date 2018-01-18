package com.kgl.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Operadora;
import com.kgl.models.Produto;
import com.kgl.enums.StatusProduto;
import com.kgl.enums.TabelaComissao;
import com.kgl.services.ProdutoService;
import com.kgl.webservices.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	ProdutoRepository dao;

	@Override
	public List<Produto> findByOperadoraAndTabelaComissao(Operadora operadora, TabelaComissao comissao) {
		List<Produto> produtos = dao.findByOperadoraAndTabelaComissao(operadora, comissao);
		return (List<Produto>) produtos.stream()
				.sorted((p1, p2) -> p1.getOperadora().getNome().compareTo(p2.getOperadora().getNome()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Produto> produtos() {
		List<Produto> produtos = (List<Produto>) dao.findAll();
		return (List<Produto>) produtos.stream()
				.sorted((p1, p2) -> p1.getOperadora().getNome().compareTo(p2.getOperadora().getNome()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Produto> produtosAtivos() {
		List<Produto> produtos = (List<Produto>) dao.findByStatusProduto(StatusProduto.ATIVO);
		return (List<Produto>) produtos.stream()
				.sorted((p1, p2) -> p1.getOperadora().getNome().compareTo(p2.getOperadora().getNome()))
				.collect(Collectors.toList());
	}

	@Override
	public void salvar(Produto p) {
		dao.save(p);
	}

	@Override
	public void deletar(Long id) {
		dao.delete(id);
	}

}
