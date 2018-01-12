package com.kgl.services;

import java.util.List;

import com.kgl.models.User;

public interface UsuarioService {
	List<User> todosUsuarios();

	User buscarUsuario(Long id);
	
	public void salvar(User u);
	
	public void deletar(Long id);



}
