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
	List<Movimentacao> findByDtPagamentoBefore(DateTime dt);
	List<Movimentacao> findByDtPagamentoBetween(DateTime dtI, DateTime dtF);

}
