package com.kgl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kgl.models.Operadora;


@RestController
public class OperadoraRestController {
	
	@Autowired
	private com.kgl.services.OperadoraService operadoraService;
	
	@RequestMapping(path="/operadorasIni", method=RequestMethod.GET)
	public List<Operadora> getAllOperadoras(){
		return operadoraService.getAllOperadoras();
	}
    @RequestMapping(value = "/operadora/{id}", method = RequestMethod.GET)
	public Operadora getOperadoraById(@PathVariable("id") long id){
		return operadoraService.getOperadoraById(id);
	}

}
