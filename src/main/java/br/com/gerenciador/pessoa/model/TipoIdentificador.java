package br.com.gerenciador.pessoa.model;

public enum TipoIdentificador {

    CPF,
    CNPJ;

    public static TipoIdentificador getByIdentificador(String identificador) {
        if (identificador == null) {
            return null;
        }

        return identificador.length() == 11 ? TipoIdentificador.CPF : TipoIdentificador.CNPJ;
    }

}
