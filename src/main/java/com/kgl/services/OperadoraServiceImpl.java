package com.kgl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Operadora;


@Service("operadoraService")
public class OperadoraServiceImpl implements OperadoraService {

	@Autowired
	private com.kgl.repository.OpRepository operadora;

	@Override
	public List<Operadora> getAllOperadoras() {
		// TODO Auto-generated method stub
		return operadora.findAll();
	}

	@Override
	public Operadora getOperadoraById(long id) {
		// TODO Auto-generated method stub
		return operadora.findOne(id);
	}




}
