package com.kgl.webservices;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Operadora;
import java.lang.Long;

@RepositoryRestResource(collectionResourceRel = "operadoraIni", path = "operadoraIni")
public interface OperadoraRepository extends PagingAndSortingRepository<Operadora, Long> {
	
	
}
