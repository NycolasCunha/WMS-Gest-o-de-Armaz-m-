package com.logistica.wms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.logistica.wms.domain.enums.CategoriaProduto;
import com.logistica.wms.domain.enums.UnidadeMedida;
import com.logistica.wms.domain.model.Produto;

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repository;

    private Produto novoProduto(String sku, String nome, CategoriaProduto categoria) {
        return Produto.builder()
                .sku(sku)
                .nome(nome)
                .categoria(categoria)
                .unidadeMedida(UnidadeMedida.UNIDADE)
                .pesoKg(new BigDecimal("1.250"))
                .build();
    }

    @Test
    void deveSalvarProdutoComValoresPadraoEDatas() {
        Produto salvo = repository.saveAndFlush(
                novoProduto("MOT-001", "Motor elétrico 5cv", CategoriaProduto.INDUSTRIAL_PESADO));

        assertNotNull(salvo.getId());
        assertNotNull(salvo.getDataCriacao());
        assertNotNull(salvo.getDataAtualizacao());
        assertTrue(salvo.isAtivo());
        assertEquals(0, salvo.getEstoqueMinimo());
    }

    @Test
    void deveNormalizarSkuParaMaiusculo() {
        Produto salvo = repository.saveAndFlush(
                novoProduto("  mot-002 ", "Motor elétrico 10cv", CategoriaProduto.INDUSTRIAL_PESADO));

        assertEquals("MOT-002", salvo.getSku());
        assertTrue(repository.findBySku("MOT-002").isPresent());
    }

    @Test
    void naoDevePermitirSkuDuplicado() {
        repository.saveAndFlush(novoProduto("DUP-001", "Produto A", CategoriaProduto.SECOS));

        assertThrows(DataIntegrityViolationException.class,
                () -> repository.saveAndFlush(novoProduto("DUP-001", "Produto B", CategoriaProduto.SECOS)));
    }

    @Test
    void deveFiltrarPorCategoriaEStatusAtivo() {
        Produto ativo = novoProduto("REF-001", "Iogurte", CategoriaProduto.REFRIGERADOS);
        Produto inativo = novoProduto("REF-002", "Queijo", CategoriaProduto.REFRIGERADOS);
        inativo.desativar();
        repository.saveAll(List.of(ativo, inativo, novoProduto("SEC-001", "Arroz", CategoriaProduto.SECOS)));

        assertEquals(2, repository.findByCategoria(CategoriaProduto.REFRIGERADOS).size());
        assertEquals(1, repository.findByCategoriaAndAtivoTrue(CategoriaProduto.REFRIGERADOS).size());
        assertEquals(2, repository.findByAtivoTrue().size());
    }

    @Test
    void deveBuscarPorNomeIgnorandoMaiusculas() {
        repository.save(novoProduto("PAR-001", "Parafuso Sextavado M10", CategoriaProduto.INDUSTRIAL_PESADO));

        assertEquals(1, repository.findByNomeContainingIgnoreCase("sextavado").size());
    }

    @Test
    void deveInformarSeProdutoExigeControleDeValidade() {
        Produto refrigerado = novoProduto("REF-003", "Leite", CategoriaProduto.REFRIGERADOS);
        Produto seco = novoProduto("SEC-002", "Sal", CategoriaProduto.SECOS);

        assertTrue(refrigerado.exigeControleValidade());
        assertFalse(seco.exigeControleValidade());
    }
}
