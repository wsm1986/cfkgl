package com.kgl.webservices;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Operadora;
import com.kgl.models.Produto;
import com.kgl.models.TabelaComissao;

@RepositoryRestResource(collectionResourceRel = "produto", path = "produto")
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long> {
	
	List<Produto>findByOperadoraAndTabelaComissao(Operadora operadora, TabelaComissao comissao);

}
