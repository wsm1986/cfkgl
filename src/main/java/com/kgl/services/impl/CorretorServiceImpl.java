package com.kgl.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Corretor;
import com.kgl.repository.CorretorRepository;
import com.kgl.services.CorretorService;
import com.kgl.services.HomeBean;

@Service("corretor")
public class CorretorServiceImpl implements CorretorService {

	@Autowired
	private CorretorRepository repository;
	
	@Autowired
	private HomeBean home;

	@Override
	public List<Corretor> todosCorretores() {
		List<Corretor> corretores = (List<Corretor>) repository.findAll();
		
		corretores = (List<Corretor>) corretores.stream().sorted((p1, p2) -> p1.getNome().compareTo(p2.getNome()))
				.collect(Collectors.toList());
		// .forEach(p -> System.out.println(p.getNome()));
		return corretores;
	}

	@Override
	public Corretor buscarCorretor(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void salvar(Corretor c) {
		// TODO Auto-generated method stub
		repository.save(c);
		
	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		repository.delete(id);
		
	}

	@Override
	public Corretor findByEmail(String email) {
		return repository.findByEmail(email);
	}


}
