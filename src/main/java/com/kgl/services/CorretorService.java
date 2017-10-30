package com.kgl.services;

import java.util.List;

import com.kgl.models.Corretor;


public interface CorretorService {
	
	public List<Corretor> todosCorretores();
	public Corretor buscarCorretor(Long id);
	public void salvar(Corretor c);
	public void excluir(Long id);
	Corretor findByEmail(String email);

	
}
