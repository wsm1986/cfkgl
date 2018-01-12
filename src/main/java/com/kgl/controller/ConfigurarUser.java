package com.kgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.GenerateHashPasswordUtil;
import com.kgl.models.User;
import com.kgl.services.UsuarioService;

@Controller
public class ConfigurarUser {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/confUser", method = RequestMethod.GET)
	public ModelAndView updatePassword(User user) throws Exception {
		ModelAndView mvn = new ModelAndView("updateUser");
		mvn.addObject("users", usuarioService.todosUsuarios());
		mvn.addObject("user", user);

		return mvn;
	}

	@RequestMapping(value = "/update/user", method = RequestMethod.POST)
	private String cadastro(User user) {
		User u = usuarioService.buscarUsuario(user.getId());
		u.setSenha(GenerateHashPasswordUtil.generateHash(user.getSenha()));
		usuarioService.salvar(u);
		return "redirect:/logout";
	}

	@RequestMapping(value = "/detalhar/{usuario}", method = RequestMethod.GET)
	public ModelAndView detalhar(@PathVariable("usuario") User usuario) throws Exception {
		return updatePassword(usuario);
	}

	@RequestMapping(value = "/remover/{usuario}", method = RequestMethod.GET)
	public ModelAndView remover(@PathVariable("usuario") User usuario) throws Exception {
		usuarioService.deletar(usuario.getId());
		return updatePassword(new User());
	}

}
