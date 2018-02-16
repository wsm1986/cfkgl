package com.kgl.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kgl.models.MessageWeb;
import com.kgl.models.Produto;


@Component
public class ProdutoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Produto.class.equals(aClass) || MessageWeb.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "parcelaKgl.primeiraParcelaKgl", "primeiraParcelaKgl.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "parcelaCorretor.primeiraParcela", "primeiraParcela.obrigatorio");

        Produto produto = (Produto) obj;
        
        if(produto .getParcelaKgl().getPrimeiraParcelaKgl() == 0) {
        	errors.rejectValue("parcelaKgl.primeiraParcelaKgl", "primeiraParcelaKgl.obrigatorio");
        }
        if(produto .getParcelaCorretor().getPrimeiraParcela() == 0) {
        	errors.rejectValue("parcelaCorretor.primeiraParcela", "primeiraParcela.obrigatorio");
        }
    }
    
}