package br.com.gerenciador.pessoa.mapper;

import br.com.gerenciador.pessoa.io.PessoaInput;
import br.com.gerenciador.pessoa.model.Pessoa;
import br.com.gerenciador.pessoa.io.PessoaOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    @Mapping(source = "identificador", target = "identificador", qualifiedByName = "apenasNumeros")
    Pessoa from(PessoaInput pessoaInput);

    PessoaOutput from(Pessoa pessoa);

    @Named("apenasNumeros")
    static String removePontuacao(String string) {
        return string != null ? string.replaceAll("[^0-9]", "") : null;
    }
}
