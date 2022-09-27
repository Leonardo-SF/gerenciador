package br.com.gerenciador.pessoa.validator;

import br.com.gerenciador.pessoa.io.PessoaInput;
import br.com.gerenciador.pessoa.model.Pessoa;
import br.com.gerenciador.pessoa.model.PessoaService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PessoaValidator implements Validator {

    private final PessoaService pessoaService;

    public PessoaValidator(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(PessoaInput.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PessoaInput input = (PessoaInput) o;

        String identificador = input.getIdentificador().replaceAll("[^0-9]", "");
        if (identificador.length() != 11 && identificador.length() != 14) {
            errors.reject("ientificador", "O identificador deve conter 11 (CPF) ou 14 (CNPJ) caracteres!");
        }

        Optional<Pessoa> pessoa = pessoaService.findByIdentificador(identificador);
        if (pessoa.isPresent()) {
            errors.reject("ientificador", "Pessoa já cadastrada!");
        }
    }

}
