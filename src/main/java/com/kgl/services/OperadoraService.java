package com.kgl.services;

import java.util.List;

import com.kgl.models.Operadora;


public interface OperadoraService {
	
	public List<Operadora> getAllOperadoras();
	public Operadora getOperadoraById(long id);
	public void save(Operadora operadora);
	public void delete(Long id);
	
}
