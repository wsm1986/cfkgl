package com.kgl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgl.models.Operadora;


@Repository("opRepository")
public interface OpRepository extends JpaRepository<Operadora, Long>{

}
