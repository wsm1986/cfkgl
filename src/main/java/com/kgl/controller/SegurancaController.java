package com.kgl.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SegurancaController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@AuthenticationPrincipal User user) {
		return "login";
	}

}