package com.kgl.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kgl.models.Contrato;
import com.kgl.models.Corretor;
import com.kgl.models.MessageWeb;
import com.kgl.models.Movimentacao;
import com.kgl.models.SubProduto;

@Component
public class CorretorValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Corretor.class.equals(aClass) || MessageWeb.class.equals(aClass) ;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        //verifica se o campo name está fazio ou apenas com espaços em branco
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "nome.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cpfCnpj", "cpf.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefone", "telefone.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "conta.banco", "banco.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "conta.agencia", "agencia.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "conta.numero", "numero.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.cep", "cep.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.rua", "rua.obrigatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endereco.numero", "endereco.obrigatorio");

        Corretor corretor = (Corretor) obj;

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