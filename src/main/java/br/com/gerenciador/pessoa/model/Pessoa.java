package br.com.gerenciador.pessoa.model;

import javax.persistence.*;

@Entity
public class Pessoa {

    @Id
    @Column(length = 14)
    private String identificador;

    private String nome;

    @Enumerated(EnumType.STRING)
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
