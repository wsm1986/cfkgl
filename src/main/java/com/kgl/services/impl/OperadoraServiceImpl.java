package com.kgl.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kgl.models.Operadora;
import com.kgl.services.OperadoraService;

@Service("operadoraService")
public class OperadoraServiceImpl implements OperadoraService {

	@Autowired
	private com.kgl.repository.OpRepository operadora;

	@Override
	@Cacheable(value = "operadoraHome")
	public List<Operadora> getAllOperadoras() {
		// TODO Auto-generated method stub
		return operadora.findAll();
	}

	@Override
	@Cacheable(value = "operadoraId")
	public Operadora getOperadoraById(long id) {
		// TODO Auto-generated method stub
		return operadora.findOne(id);
	}

	@Override
	@CacheEvict(value = {"operadoraHome","operadoraId"}, allEntries = true)
	public void save(Operadora op) {
		operadora.save(op);
	}

	@Override
	@CacheEvict(value = {"operadoraHome","operadoraId"}, allEntries = true)
	public void delete(Long id) {
		operadora.delete(id);
	}

}
