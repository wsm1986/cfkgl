package com.kgl.webservices;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.SubProduto;

@RepositoryRestResource(collectionResourceRel = "subproduto", path = "ProdutoRepository.java")
public interface SubProdutoRepository extends PagingAndSortingRepository<SubProduto, Long> {
	

}
