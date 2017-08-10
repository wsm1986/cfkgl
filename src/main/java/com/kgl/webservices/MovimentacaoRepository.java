package com.kgl.webservices;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.Operadora;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "movimentacao", path = "movimentacao")
public interface MovimentacaoRepository extends PagingAndSortingRepository<Movimentacao, Long> {
	List<Movimentacao> findByContrato(Contrato contrato);

}
