package com.kgl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kgl.models.MessageWeb;
import com.kgl.models.Operadora;
import com.kgl.services.OperadoraService;

@Controller
@RequestMapping("/operadora")
public class OperadoraController {

	@Autowired
	OperadoraService operadoraService;

	@RequestMapping({ "/form" })
	public ModelAndView form(Operadora operadora) {
		ModelAndView mvn = new ModelAndView("operadora/novo");
		mvn.addObject("operadora", operadora);
		mvn.addObject("operadoras", operadoraService.getAllOperadoras());
		return mvn;
	}

	@RequestMapping({ "/salvar" })
	public ModelAndView salvar(@Valid Operadora operadora, BindingResult result, HttpServletRequest request,
			RedirectAttributes attributes) {
		String referer = request.getHeader("Referer");

		try {
			if (result.hasErrors()) {
				return form(operadora);
			}

			operadoraService.save(operadora);

			attributes.addFlashAttribute(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.SUCCESS_ALTER);
		} catch (Exception e) {
			attributes.addFlashAttribute(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_SAVE);
		}
		return new ModelAndView("redirect:" + referer);
	}

	@RequestMapping("/remover/{id}")
	private ModelAndView remover(@PathVariable("id") Long id, HttpServletRequest request,
			RedirectAttributes attributes) {
		String referer = request.getHeader("Referer");
		try {
			operadoraService.delete(id);
		} catch (Exception e) {
			attributes.addFlashAttribute(MessageWeb.MESSAGE_ATTRIBUTE, MessageWeb.ERROR_SAVE);
		}
		return new ModelAndView("redirect:" + referer);
	}

	@RequestMapping("/listar")
	private ModelAndView listar() {
		ModelAndView mvn = new ModelAndView("operadora/operadoras");
		return mvn;
	}
}
