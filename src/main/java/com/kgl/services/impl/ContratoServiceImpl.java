package com.kgl.services.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kgl.models.Contrato;
import com.kgl.services.ContratoService;
import com.kgl.services.CorretorService;
import com.kgl.services.HomeBean;
import com.kgl.webservices.ContratoRepository;

import ch.qos.logback.classic.Logger;

@Service("contratoService")
public class ContratoServiceImpl implements ContratoService {

	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(ContratoServiceImpl.class);

	@Autowired
	ContratoRepository contratoRepository;

	@Autowired
	private HomeBean home;

	@Autowired
	CorretorService corretorService;

	@Override
	@Cacheable(value = "contratoHome")
	public List<Contrato> buscarContrato() {
		try {
			if (home.permissaoUsuario()) {
				return (List<Contrato>) contratoRepository.findAll();
			} else {
				String email = home.emailLogado();
				return contratoRepository.findByCorretor(corretorService.findByEmail(email));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Override
	@CacheEvict(value = { "movimentacaoHome", "contratoHome" }, allEntries = true)
	public void salvar(Contrato contrato) {
		try {
			contratoRepository.save(contrato);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	@CacheEvict(value = { "movimentacaoHome", "contratoHome" }, allEntries = true)
	public void excluir(Long id) {
		try {
			contratoRepository.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public Contrato buscarContrato(Long id) {
		try {
			return contratoRepository.findOne(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Contrato();
		}
	}

}
