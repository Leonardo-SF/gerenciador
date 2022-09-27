package br.com.gerenciador.pessoa.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

@Service
class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        pessoa.setTipoIdentificador(TipoIdentificador.getByIdentificador(pessoa.getIdentificador()));
        return this.pessoaRepository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public Optional<Pessoa> findByIdentificador(String identificador) {
        return this.pessoaRepository.findById(identificador);
    }

    @Transactional(readOnly = true)
    public Page<Pessoa> findAll(Pageable pageable) {
        return this.pessoaRepository.findAll(pageable);
    }

    @Transactional
    public Pessoa update(String identificador, String nome) {
        Optional<Pessoa> pessoa = this.findByIdentificador(identificador);
        if (pessoa.isEmpty()) {
            return null;
        }

        pessoa.get().setNome(nome);
        return pessoa.get();
    }

    public void delete(Pessoa pessoa) {
        this.pessoaRepository.delete(pessoa);
    }
}
