package com.kgl.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeBean {

	@Autowired
	HttpSession session;
	
	public Boolean permissaoUsuario() {
		Boolean permissao =  (Boolean) session.getAttribute("permissao");
		return permissao;

	}
	public String emailLogado() {
		return (String)session.getAttribute("emailCorretor");

	}
	public Long idCorretor() {
		return (Long)session.getAttribute("corretorId");

	}
	
	
}
