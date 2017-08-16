package com.kgl.repository;

import org.springframework.data.repository.CrudRepository;

import com.kgl.models.User;


public interface UserRepository extends CrudRepository<User, Long> {
	
	com.kgl.models.User findByUserName(String name);

}
