package com.logistica.wms.domain.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CategoriaProdutoTest {

    @Test
    void refrigeradosExigemControleDeTemperatura() {
        assertTrue(CategoriaProduto.REFRIGERADOS.exigeControleTemperatura());
        assertTrue(CategoriaProduto.REFRIGERADOS.aceitaTemperatura(5));
        assertFalse(CategoriaProduto.REFRIGERADOS.aceitaTemperatura(12));
    }

    @Test
    void secosAceitamQualquerTemperatura() {
        assertFalse(CategoriaProduto.SECOS.exigeControleTemperatura());
        assertTrue(CategoriaProduto.SECOS.aceitaTemperatura(35));
    }

    @Test
    void inflamaveisExigemAreaSegregada() {
        assertTrue(CategoriaProduto.INFLAMAVEIS.isExigeAreaSegregada());
    }

    @Test
    void buscaPorZonaRetornaCategoriaCorreta() {
        assertEquals(1, CategoriaProduto.porZona("zn-con").size());
        assertEquals(CategoriaProduto.CONGELADOS, CategoriaProduto.porZona("ZN-CON").get(0));
    }
}
