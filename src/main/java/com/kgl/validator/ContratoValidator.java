package com.kgl.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kgl.models.Contrato;
import com.kgl.models.Movimentacao;
import com.kgl.models.SubProduto;

@Component
public class ContratoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Contrato.class.equals(aClass) || SubProduto.class.equals(aClass) || Movimentacao.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        //verifica se o campo name está fazio ou apenas com espaços em branco
    	if(obj instanceof SubProduto) {
            SubProduto subProduto = (SubProduto) obj;
            
            if(subProduto.getProduto() == null) {
            	errors.rejectValue("produto", "produto.obrigatorio");
            }
            if(subProduto.getDescricao().isEmpty()) {
            	errors.rejectValue("descricao", "descricao.obrigatorio");
            }
    	}else {
	    	Contrato contrato = (Contrato) obj;
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codigoContrato", "codigoContrato.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dtCadastro", "dtCadastro.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dtAssinatura", "dtAssinatura.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dtVigencia", "dtVigencia.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "valor", "valor.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "segurado.nome", "segurado.nome.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "segurado.cpfCnpj", "segurado.cpfCnpj.obrigatorio");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "segurado.quantVidas", "segurado.quantVidas.obrigatorio");
	
	        
	        if(contrato.getCorretor() == null) {
	        	errors.rejectValue("corretor", "corretor.obrigatorio");
	        }
	        
	        if(contrato.getProduto() == null) {
	        	errors.rejectValue("subProduto", "subProduto.obrigatorio");
	        }
    	}
        //se o objeto profile for null o estoura o erro
        /*
        if (corretor.getNome() == null || corretor.getNome().isEmpty()) {
            errors.rejectValue("nome", "nome.obrigatorio");
        }
        //se o objeto birthDate for null estoura o erro
        if (user.getBirthDate() == null) {
            errors.rejectValue("birthDate", "required.birthDate");
        }*/
    }
}