package com.kgl.services.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Response;
import com.kgl.enums.StatusContrato;
import com.kgl.enums.StatusMovimentacao;
import com.kgl.enums.TipoPesquisaMovimentacao;
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
		try {
			if (home.permissaoUsuario() != null && !home.permissaoUsuario()) {
				response.setCorretor(home.idCorretor().toString());
			}
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			if (response.tipoPesquisa().equals(TipoPesquisaMovimentacao.ENTRE)) {
				DateTime dt = formatter.parseDateTime(response.getDtInicial());
				DateTime dtF = formatter.parseDateTime(response.getDtFinal());
				return dao.findByDtPagamentoBetween(dt, dtF);
			} else if (response.tipoPesquisa().equals(TipoPesquisaMovimentacao.CORRETOR)) {
				return dao.findByContratoCorretorId(Long.valueOf(response.getCorretor()));
			} else if (response.tipoPesquisa().equals(TipoPesquisaMovimentacao.APARTIR)) {
				DateTime dt = formatter.parseDateTime(response.getDtInicial());
				return dao.findByDtPagamentoAfter(dt);
			} else if (response.tipoPesquisa().equals(TipoPesquisaMovimentacao.FILTRO_COMPLETO)) {
				DateTime dt = formatter.parseDateTime(response.getDtInicial());
				DateTime dtF = formatter.parseDateTime(response.getDtFinal());
				return dao.findByDtPagamentoBetweenAndContratoCorretorId(dt, dtF, Long.valueOf(response.getCorretor()));
			} else if (response.tipoPesquisa().equals(TipoPesquisaMovimentacao.CORRETOR_APARTIR)) {
				DateTime dt = formatter.parseDateTime(response.getDtInicial());
				return dao.findByDtPagamentoAfterAndContratoCorretorId(dt, Long.valueOf(response.getCorretor()));
			} else {
				if (home.permissaoUsuario()) {
					return (List<Movimentacao>) dao.findAll();
				} else {
					return dao.findByContratoCorretorId(Long.valueOf(response.getCorretor()));
				}
			}
		} catch (Exception e) {
			System.out.println("Erro");
		}
		return null;
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
	@Cacheable(value = "movimentacaoHome")
	public List<Movimentacao> buscarMovimentacoes() {
		if (home.permissaoUsuario()) {
			return (List<Movimentacao>) dao.findAll();
		} else {
			return dao.findByContratoCorretorEmail(home.emailLogado());
		}
	}

	@Override
	@CacheEvict(value = "movimentacaoHome", allEntries = true)
	public void gerarMovimentacao(Contrato contrato) {
		for (int mesPagamento = 0; mesPagamento < 12; mesPagamento++) {
			Movimentacao mov = new Movimentacao();
			mov.setContrato(contrato);
			mov.setStatus(StatusMovimentacao.AGUARDADO_PAGAMENTO);

			if (mesPagamento == 0) {
				mov.setTaxa(new Double(0));

			} else {
				mov.setTaxa(new Double(8.5));
			}

			// Caso seja a segunda parcela cobrar tarifa
			if (mesPagamento == 1) {
				mov.setTarifa(contrato.getTarifa());
				mov.setValorCorretor(contrato.getProduto().getParcelaCorretor().calcularValorLucro(contrato.getValor(),
						mesPagamento));

			} else {
				mov.setTarifa(new BigDecimal(0));
				;
				mov.setValorCorretor(contrato.getProduto().getParcelaCorretor().calcularValorLucro(contrato.getValor(),
						mesPagamento));
			}
			mov.setValorKgl(
					contrato.getProduto().getParcelaKgl().calcularValorLucro(contrato.getValor(), mesPagamento));
			mov.setLucro(mov.getValorKgl().subtract(mov.getValorCorretor()));
			mov.setDtPagamento(contrato.getDtCadastro().plusMonths(mesPagamento));

			if (mov.getValorKgl().compareTo(BigDecimal.ZERO) == 1) {
				dao.save(mov);
			}
		}
	}

	@Override
	public void atualizarContrato(Movimentacao mov, HttpSession session, String flag) {
		if(!mov.getContrato().getStatusContrato().equals(StatusContrato.RECUSADO)) {
		BigDecimal vlr = (BigDecimal) session.getAttribute("vlrCorretor");
		if ("S".equals(flag)) {
			mov.setStatus(StatusMovimentacao.PAGO);
			session.setAttribute("vlrCorretor", vlr.subtract(mov.getValorCorretor()));

		} else if ("N".equals(flag)) {
			mov.setStatus(StatusMovimentacao.RECUSADO);
		} else if ("A".equals(flag)) {
			session.setAttribute("vlrCorretor", vlr.add(mov.getValorCorretor()));
			mov.setStatus(StatusMovimentacao.AGUARDADO_PAGAMENTO);
		}
		}
		
	}

	@Override
	public Movimentacao findById(Long id) {
		// TODO Auto-generated method stub
		return dao.findOne(id);
	}

}
