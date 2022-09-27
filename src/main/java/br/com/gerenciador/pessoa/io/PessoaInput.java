package br.com.gerenciador.pessoa.io;

import javax.validation.constraints.NotBlank;

public class PessoaInput {

    @NotBlank(message = "É necessário informar o identificador!")
    private String identificador;

    private String nome;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
