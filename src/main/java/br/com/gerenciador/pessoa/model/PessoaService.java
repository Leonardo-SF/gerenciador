package br.com.gerenciador.pessoa.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PessoaService {

    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findByIdentificador(String identificador);

    Page<Pessoa> findAll(Pageable pageable);

    Pessoa update(String identificador, String nome);

    void delete(Pessoa pessoa);

}
