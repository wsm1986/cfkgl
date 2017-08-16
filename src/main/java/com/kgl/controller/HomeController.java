package com.kgl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping({ "/", "/index" })
	private ModelAndView form(Contrato contrato) {
		ModelAndView mvn = new ModelAndView("index");
		return mvn;

	}	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-maluca-/{email}")
	public String urlMagicaMaluca(@PathVariable("email") String email) {
		List<User> listUser = (List<User>) userRepository.findAll();
		String password;
		List<Role> list = new ArrayList();
		if (listUser.isEmpty()) {
			password = GenerateHashPasswordUtil.generateHash("1234");
			Role role = new Role();
			role.setNome("ROLE_USER");
			list.add(role);
			User user = new User("admin@gmail.com",password,list);
			userRepository.save(user);
			role = new Role();
			role.setNome("ROLE_ADMIN");
			list = new ArrayList<>();
			list.add(role);
			user = new User("karina@gmail.com",password,list);
			userRepository.save(user);
			
		}
		//User user = new User("admin@gmail.com",password,list);		
		return "Url Mágica executada";
	}	

}
