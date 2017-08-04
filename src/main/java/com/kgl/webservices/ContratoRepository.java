package com.kgl.webservices;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Contrato;
import com.kgl.models.Operadora;

@RepositoryRestResource(collectionResourceRel = "contrato", path = "contrato")
public interface ContratoRepository extends PagingAndSortingRepository<Contrato, Long> {

}
