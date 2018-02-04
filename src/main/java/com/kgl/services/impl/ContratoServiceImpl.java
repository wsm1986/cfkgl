package com.kgl.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kgl.models.Contrato;
import com.kgl.services.ContratoService;
import com.kgl.services.CorretorService;
import com.kgl.services.HomeBean;
import com.kgl.webservices.ContratoRepository;

@Service("contratoService")
public class ContratoServiceImpl implements ContratoService {

	@Autowired
	ContratoRepository contratoRepository;
	
	@Autowired
	private HomeBean home;

	@Autowired
	CorretorService corretorService;

	@Override
	@Cacheable(value = "contratoHome")
	public List<Contrato> buscarContrato() {

		if (home.permissaoUsuario()) {
			return (List<Contrato>) contratoRepository.findAll();
		} else {
			String email = home.emailLogado();
			return  contratoRepository.findByCorretor(corretorService.findByEmail(email));
		}
	}

	@Override
	@CacheEvict(value= {"movimentacaoHome","contratoHome"},allEntries=true)
	public void salvar(Contrato contrato) {
		contratoRepository.save(contrato);

	}

	@Override
	@CacheEvict(value= {"movimentacaoHome","contratoHome"},allEntries=true)
	public void excluir(Long id) {
		contratoRepository.delete(id);

	}

	@Override
	public Contrato buscarContrato(Long id) {
		return contratoRepository.findOne(id);
	}

}
