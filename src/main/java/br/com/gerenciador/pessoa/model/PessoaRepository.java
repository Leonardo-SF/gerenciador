package br.com.gerenciador.pessoa.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PessoaRepository extends JpaRepository<Pessoa,String>, JpaSpecificationExecutor<Pessoa> {

}
