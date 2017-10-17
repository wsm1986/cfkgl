package com.kgl.repository;


import org.springframework.data.repository.CrudRepository;

import com.kgl.models.Corretor;

public interface CorretorRepository extends CrudRepository<Corretor, Long>{
	Corretor findByEmail(String email);
}
