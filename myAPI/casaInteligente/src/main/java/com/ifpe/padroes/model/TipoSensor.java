
package com.ifpe.padroes.model;

public enum TipoSensor {
    SEGURANCA("Segurança"),
    ILUMINACAO("Iluminação"),
    TEMPERATURA("Temperatura"),
    PRESENCA("Presença"),
    OUTRO("Outro");

    private final String descricao;

    TipoSensor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
