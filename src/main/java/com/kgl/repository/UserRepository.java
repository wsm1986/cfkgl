package com.kgl.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<com.kgl.models.User, Long> {
	
	com.kgl.models.User findByUserName(String name);

}
