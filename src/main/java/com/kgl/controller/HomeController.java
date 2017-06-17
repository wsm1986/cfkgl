package com.kgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@Autowired
	@RequestMapping({ "/", "/index", "/index.html" })
	public ModelAndView index() {
		return new ModelAndView("kgl");
	}

}
