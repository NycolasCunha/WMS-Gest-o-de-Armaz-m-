package com.logistica.wms.domain.enums;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Categorias de produto e suas respectivas zonas de estocagem.
 *
 * <p>Cada categoria define as condições de armazenagem exigidas, o que
 * permite ao endereçamento dinâmico sugerir apenas vagas compatíveis.
 * Temperaturas nulas indicam armazenagem em temperatura ambiente.</p>
 */
@Getter
@RequiredArgsConstructor
public enum CategoriaProduto {

    SECOS("Produtos secos e não perecíveis", "ZN-SEC", null, null, false, false),
    REFRIGERADOS("Produtos refrigerados", "ZN-REF", 2, 8, true, false),
    CONGELADOS("Produtos congelados", "ZN-CON", -25, -15, true, false),
    MEDICAMENTOS("Medicamentos e produtos farmacêuticos", "ZN-MED", 15, 25, true, false),
    INFLAMAVEIS("Produtos inflamáveis e químicos", "ZN-INF", null, null, true, true),
    FRAGEIS("Produtos frágeis", "ZN-FRG", null, null, false, false),
    ELETROELETRONICOS("Eletroeletrônicos e componentes", "ZN-ELE", null, null, false, false),
    INDUSTRIAL_PESADO("Peças industriais e cargas pesadas", "ZN-PES", null, null, false, false);

    private final String descricao;
    private final String zonaEstocagem;
    private final Integer temperaturaMinima;
    private final Integer temperaturaMaxima;
    private final boolean controlaValidade;
    private final boolean exigeAreaSegregada;

    public boolean exigeControleTemperatura() {
        return temperaturaMinima != null && temperaturaMaxima != null;
    }

    public boolean aceitaTemperatura(int temperaturaCelsius) {
        if (!exigeControleTemperatura()) {
            return true;
        }
        return temperaturaCelsius >= temperaturaMinima && temperaturaCelsius <= temperaturaMaxima;
    }

    public static List<CategoriaProduto> porZona(String zonaEstocagem) {
        return Arrays.stream(values())
                .filter(categoria -> categoria.zonaEstocagem.equalsIgnoreCase(zonaEstocagem))
                .toList();
    }
}