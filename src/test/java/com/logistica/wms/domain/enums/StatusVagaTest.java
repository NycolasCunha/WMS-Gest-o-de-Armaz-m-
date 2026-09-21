package com.logistica.wms.domain.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StatusVagaTest {

    @Test
    void vagaLivrePodeSerReservada() {
        assertTrue(StatusVaga.LIVRE.podeTransitarPara(StatusVaga.RESERVADA));
    }

    @Test
    void vagaOcupadaNaoPodeSerReservada() {
        assertFalse(StatusVaga.OCUPADA.podeTransitarPara(StatusVaga.RESERVADA));
    }

    @Test
    void transicaoParaOMesmoStatusENulaSaoInvalidas() {
        assertFalse(StatusVaga.LIVRE.podeTransitarPara(StatusVaga.LIVRE));
        assertFalse(StatusVaga.LIVRE.podeTransitarPara(null));
    }

    @Test
    void somenteVagaLivreEstaDisponivel() {
        assertTrue(StatusVaga.LIVRE.estaDisponivel());
        assertFalse(StatusVaga.OCUPADA.estaDisponivel());
        assertFalse(StatusVaga.RESERVADA.estaDisponivel());
    }
}
