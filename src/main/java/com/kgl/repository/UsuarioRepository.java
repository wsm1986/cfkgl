package com.kgl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.kgl.models.User;


@Repository
public class UsuarioRepository implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String login) {
		User usuario = userRepository.findByUserName(login);

		if (usuario == null) {
			throw new UsernameNotFoundException("O usuário " + login + " não foi encontrado");
		}

		return usuario;
	}

}