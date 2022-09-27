package br.com.gerenciador.pessoa.io;

import br.com.gerenciador.pessoa.model.TipoIdentificador;

public class PessoaOutput {
    private String identificador;

    private String nome;

    private TipoIdentificador tipoIdentificador;

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

    public TipoIdentificador getTipoIdentificador() {
        return tipoIdentificador;
    }

    public void setTipoIdentificador(TipoIdentificador tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }
}
