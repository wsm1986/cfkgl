package com.kgl.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;
import com.kgl.models.TipoPesquisaMovimentacao;
import com.kgl.services.HomeBean;
import com.kgl.services.MovimentacaoService;
import com.kgl.webservices.MovimentacaoRepository;

@Service("mov")
public class MovimentacaoServiceImpl implements MovimentacaoService {
	
	@Autowired
	private HomeBean home;
	
	

	@Autowired
	MovimentacaoRepository dao;

	@Override
	public List<Movimentacao> findByContrato(Contrato contrato) {
		return dao.findByContrato(contrato);

	}

	@Override
	public List<Movimentacao> findByContratoCorretorId(Long id) {
		return findByContratoCorretorId(id);
	}


	@Override
	public List<Movimentacao> findByContratoId(Long id) {
		return dao.findByContratoCorretorId(id);
	}

	@Override
	public List<Movimentacao> buscarMovimentacao(Response response) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.ENTRE)) {
			DateTime dt = formatter.parseDateTime(response.getDtInicial());
			DateTime dtF = formatter.parseDateTime(response.getDtFinal());
			return dao.findByDtPagamentoBetween(dt, dtF);
		}else if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.CORRETOR)) {
			return dao.findByContratoCorretorId(Long.valueOf(response.getCorretor()));
		}else if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.APARTIR)) {
			DateTime dt = formatter.parseDateTime(response.getDtInicial());
			return dao.findByDtPagamentoAfter(dt);
		}else if(response.tipoPesquisa().equals(TipoPesquisaMovimentacao.FILTRO_COMPLETO)) {
			DateTime dt = formatter.parseDateTime(response.getDtInicial());
			DateTime dtF = formatter.parseDateTime(response.getDtFinal());
			return dao.findByDtPagamentoBetweenAndContratoCorretorId(dt, dtF, Long.valueOf(response.getCorretor()));
		}else {
			return (List<Movimentacao>) dao.findAll();
		}
	}

	@Override
	public void salvar(Movimentacao mov) {
		dao.save(mov);
		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
		
	}

	@Override
	public List<Movimentacao> buscarMovimentacoes() {
		if(home.permissaoUsuario()) {
			return (List<Movimentacao>) dao.findAll();
		}else {
			return dao.findByContratoCorretorEmail(home.emailLogado());
		}
	}


}
