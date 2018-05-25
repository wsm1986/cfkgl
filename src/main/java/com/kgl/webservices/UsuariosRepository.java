package com.kgl.webservices;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kgl.models.Contrato;
import com.kgl.models.Corretor;
import com.kgl.models.Operadora;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface ContratoRepository extends PagingAndSortingRepository<User, Long> {
}
