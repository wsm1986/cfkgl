package com.kgl.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgl.models.User;
import com.kgl.repository.UserRepository;
import com.kgl.services.HomeBean;
import com.kgl.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private HomeBean home;

	@Override
	public List<User> todosUsuarios() {
		List<User> users;
		if (home.permissaoUsuario()) {
			users = (List<User>) repository.findAll();
		} else {
			String email = home.emailLogado();
			users = (List<User>) repository.findByUserName(email);
		}
		return users;
	}

	@Override
	public User buscarUsuario(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	public void salvar(User u) {
		repository.save(u);
	}

	@Override
	public void deletar(Long id) {
		repository.delete(id);

	}

}
