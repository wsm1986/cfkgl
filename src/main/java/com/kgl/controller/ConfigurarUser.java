package com.kgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.GenerateHashPasswordUtil;
import com.kgl.models.User;
import com.kgl.repository.UserRepository;
import com.kgl.services.HomeBean;

@Controller
public class ConfigurarUser {

	@Autowired
	private UserRepository repository;
	
	
	@Autowired
	private HomeBean home;

	@RequestMapping(value = "/confUser", method = RequestMethod.GET)
	public ModelAndView updatePassword(User user) throws Exception {
		ModelAndView mvn = new ModelAndView("updateUser");
		if(home.permissaoUsuario()) {
			mvn.addObject("users", repository.findAll());
		}else {
			String email = home.emailLogado();
			mvn.addObject("users", repository.findByUserName(email));
		}
		
		mvn.addObject("user", user);

		return mvn;
	}

	@RequestMapping(value = "/update/user", method = RequestMethod.POST)
	private String cadastro(User user) {
		User u = repository.findOne(user.getId());
		u.setSenha(GenerateHashPasswordUtil.generateHash(user.getSenha()));
		repository.save(u);
		return "redirect:/logout";
	}
	@RequestMapping(value = "/detalhar/{usuario}", method = RequestMethod.GET)
	public ModelAndView detalhar(@PathVariable("usuario") User usuario) throws Exception {
		return updatePassword(usuario);
	}
	@RequestMapping(value = "/remover/{usuario}", method = RequestMethod.GET)
	public ModelAndView remover(@PathVariable("usuario") User usuario) throws Exception {
		repository.delete(usuario);
		return updatePassword(new User());
	}	
	
}
