package com.kgl.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kgl.models.Corretor;
import com.kgl.repository.CorretorRepository;
import com.kgl.validator.CorretorValidator;

@Controller
@RequestMapping("/corretor")
public class CorretorController {

	@Autowired
	private CorretorRepository dao;

	@Autowired
	private CorretorValidator corretorValidation;

	@RequestMapping("/form")
	private ModelAndView form(Corretor Corretor) {
		ModelAndView mvn = new ModelAndView("corretor/novo");
		mvn.addObject("corretor", Corretor);
		return mvn;
	}

	@RequestMapping("novo")
	private ModelAndView novo(@Valid Corretor corretor, BindingResult result) {
		if (result.hasErrors()) {
			return form(corretor);
		}
		corretor.getConta().setTitular(corretor.getNome());
		corretor.setDtInclusao(Calendar.getInstance());
		ModelAndView mvn = new ModelAndView("corretor/novo");
		dao.save(corretor);
		return mvn;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.setValidator(corretorValidation);
	}
}
