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

@Controller
public class ConfigurarUser {

	@Autowired
	private UserRepository repository;

	@RequestMapping(value = "/confUser", method = RequestMethod.GET)
	public ModelAndView updatePassword(User user) throws Exception {
		ModelAndView mav = new ModelAndView("updateUser");
		mav.addObject("users", repository.findAll());
		mav.addObject("user", user);

		return mav;
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
	
}
