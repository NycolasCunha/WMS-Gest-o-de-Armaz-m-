package com.logistica.wms.domain.enums;

import java.util.EnumSet;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Estados possíveis de uma vaga (posição de armazenagem) no armazém.
 *
 * <p>Também encapsula a máquina de estados: apenas as transições
 * listadas em {@link #podeTransitarPara(StatusVaga)} são permitidas.</p>
 */
@Getter
@RequiredArgsConstructor
public enum StatusVaga {

    LIVRE("Livre", "Vaga vazia e disponível para armazenagem"),
    RESERVADA("Reservada", "Vaga reservada para um recebimento em andamento"),
    OCUPADA("Ocupada", "Vaga com mercadoria armazenada"),
    BLOQUEADA("Bloqueada", "Vaga bloqueada por divergência, avaria ou inventário"),
    EM_MANUTENCAO("Em manutenção", "Estrutura da vaga em reparo ou inspeção");

    private final String descricao;
    private final String detalhe;

    public boolean estaDisponivel() {
        return this == LIVRE;
    }

    public boolean estaIndisponivelParaOperacao() {
        return this == BLOQUEADA || this == EM_MANUTENCAO;
    }

    public boolean podeTransitarPara(StatusVaga destino) {
        if (destino == null || destino == this) {
            return false;
        }
        return transicoesPermitidas().contains(destino);
    }

    private Set<StatusVaga> transicoesPermitidas() {
        return switch (this) {
            case LIVRE -> EnumSet.of(RESERVADA, OCUPADA, BLOQUEADA, EM_MANUTENCAO);
            case RESERVADA -> EnumSet.of(LIVRE, OCUPADA, BLOQUEADA);
            case OCUPADA -> EnumSet.of(LIVRE, BLOQUEADA);
            case BLOQUEADA -> EnumSet.of(LIVRE, EM_MANUTENCAO);
            case EM_MANUTENCAO -> EnumSet.of(LIVRE, BLOQUEADA);
        };
    }
}