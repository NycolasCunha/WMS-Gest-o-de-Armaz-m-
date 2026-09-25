package com.logistica.wms.domain.enums;

public enum UnidadeMedida {

    UNIDADE("Unidade", "un"),
    CAIXA("Caixa", "cx"),
    PECA("Peça", "pç"),
    QUILOGRAMA("Quilograma", "kg"),
    LITRO("Litro", "l"),
    PALETE("Palete", "plt");

    private final String descricao;
    private final String simbolo;

    UnidadeMedida(String descricao, String simbolo) {
        this.descricao = descricao;
        this.simbolo = simbolo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
