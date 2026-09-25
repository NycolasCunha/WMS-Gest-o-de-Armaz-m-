package com.logistica.wms.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.logistica.wms.domain.enums.CategoriaProduto;
import com.logistica.wms.domain.enums.UnidadeMedida;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "produtos",
        uniqueConstraints = @UniqueConstraint(name = "uk_produto_sku", columnNames = "sku"),
        indexes = {
                @Index(name = "idx_produto_categoria", columnList = "categoria"),
                @Index(name = "idx_produto_ativo", columnList = "ativo")
        })
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String sku;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaProduto categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida", nullable = false, length = 20)
    private UnidadeMedida unidadeMedida;

    @Column(name = "peso_kg", precision = 10, scale = 3)
    private BigDecimal pesoKg;

    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoqueMinimo = 0;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Produto() {}

    public Produto(Long id, String sku, String nome, String descricao, CategoriaProduto categoria,
                   UnidadeMedida unidadeMedida, BigDecimal pesoKg, Integer estoqueMinimo, boolean ativo,
                   LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.pesoKg = pesoKg;
        this.estoqueMinimo = estoqueMinimo != null ? estoqueMinimo : 0;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    @PrePersist
    void aoCriar() {
        normalizar();
        LocalDateTime agora = LocalDateTime.now();
        this.dataCriacao = agora;
        this.dataAtualizacao = agora;
    }

    @PreUpdate
    void aoAtualizar() {
        normalizar();
        this.dataAtualizacao = LocalDateTime.now();
    }

    private void normalizar() {
        if (sku != null) {
            this.sku = sku.trim().toUpperCase();
        }
        if (nome != null) {
            this.nome = nome.trim();
        }
    }

    public boolean exigeControleValidade() {
        return categoria != null && categoria.isControlaValidade();
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public CategoriaProduto getCategoria() { return categoria; }
    public void setCategoria(CategoriaProduto categoria) { this.categoria = categoria; }

    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public static ProdutoBuilder builder() {
        return new ProdutoBuilder();
    }

    public static class ProdutoBuilder {
        private Long id;
        private String sku;
        private String nome;
        private String descricao;
        private CategoriaProduto categoria;
        private UnidadeMedida unidadeMedida;
        private BigDecimal pesoKg;
        private Integer estoqueMinimo = 0;
        private boolean ativo = true;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataAtualizacao;

        public ProdutoBuilder id(Long id) { this.id = id; return this; }
        public ProdutoBuilder sku(String sku) { this.sku = sku; return this; }
        public ProdutoBuilder nome(String nome) { this.nome = nome; return this; }
        public ProdutoBuilder descricao(String descricao) { this.descricao = descricao; return this; }
        public ProdutoBuilder categoria(CategoriaProduto categoria) { this.categoria = categoria; return this; }
        public ProdutoBuilder unidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; return this; }
        public ProdutoBuilder pesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; return this; }
        public ProdutoBuilder estoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; return this; }
        public ProdutoBuilder ativo(boolean ativo) { this.ativo = ativo; return this; }
        public ProdutoBuilder dataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; return this; }
        public ProdutoBuilder dataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; return this; }

        public Produto build() {
            return new Produto(id, sku, nome, descricao, categoria, unidadeMedida, pesoKg, estoqueMinimo, ativo, dataCriacao, dataAtualizacao);
        }
    }
}
