package com.kgl.webservices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.kgl.models.Operadora;
import java.lang.Long;

@RepositoryRestResource(collectionResourceRel = "operadora", path = "operadora")
public interface OperadoraRepository extends PagingAndSortingRepository<Operadora, Long> {
	
	
}
