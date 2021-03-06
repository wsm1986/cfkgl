package com.kgl.webservices;

import org.joda.time.DateTime;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "movimentacao", path = "movimentacao")
public interface MovimentacaoRepository extends PagingAndSortingRepository<Movimentacao, Long> {
	List<Movimentacao> findByContrato(Contrato contrato);
	List<Movimentacao> findByContratoCorretorId(Long id);
	List<Movimentacao> findByContratoCorretorEmail(String email);
	List<Movimentacao> findByContratoId(Long id);
	List<Movimentacao> findByDtPagamentoAfter(DateTime dt);
	List<Movimentacao> findByDtPagamentoBetween(DateTime dtI, DateTime dtF);
	List<Movimentacao> findByDtPagamentoBetweenAndContratoCorretorId(DateTime dtI, DateTime dtF, Long id);
	List<Movimentacao> findByDtPagamentoAfterAndContratoCorretorId(DateTime dtI, Long id);
	List<Movimentacao> findByDtPagamentoKglAfter(DateTime dt);
	List<Movimentacao> findByDtPagamentoKglBetween(DateTime dtI, DateTime dtF);
	List<Movimentacao> findByDtPagamentoKglBetweenAndContratoCorretorId(DateTime dtI, DateTime dtF, Long id);
	List<Movimentacao> findByDtPagamentoKglAfterAndContratoCorretorId(DateTime dtI, Long id);



}
