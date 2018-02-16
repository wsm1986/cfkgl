package com.kgl.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kgl.models.MessageWeb;
import com.kgl.models.SubProduto;

@Component
public class SubProdutoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return SubProduto.class.equals(aClass) || MessageWeb.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        //verifica se o campo name está fazio ou apenas com espaços em branco

        SubProduto subProduto = (SubProduto) obj;
        
        if(subProduto.getProduto() == null) {
        	errors.rejectValue("produto", "produto.obrigatorio");
        }
        if(subProduto.getDescricao().isEmpty()) {
        	errors.rejectValue("descricao", "descricao.obrigatorio");
        }
    }
}