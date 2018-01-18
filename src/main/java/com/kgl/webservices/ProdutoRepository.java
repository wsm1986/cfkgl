package com.kgl.webservices;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Operadora;
import com.kgl.models.Produto;
import com.kgl.enums.StatusProduto;
import com.kgl.enums.TabelaComissao;

@RepositoryRestResource(collectionResourceRel = "produto", path = "produto")
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long> {

	List<Produto> findByOperadoraAndTabelaComissao(Operadora operadora, TabelaComissao comissao);

	
	List<Produto> findByStatusProduto(StatusProduto status);

	@Modifying
	@Query("UPDATE Produto p SET p.statusProduto = :status")
	void ativarAll(@Param("status") StatusProduto ativo);

}
