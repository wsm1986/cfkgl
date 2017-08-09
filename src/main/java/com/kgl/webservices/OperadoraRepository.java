package com.kgl.webservices;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Operadora;
import java.lang.Long;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "operadora", path = "operadora")
public interface OperadoraRepository extends PagingAndSortingRepository<Operadora, Long> {

}
