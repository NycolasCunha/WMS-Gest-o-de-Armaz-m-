package com.logistica.wms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistica.wms.domain.enums.CategoriaProduto;
import com.logistica.wms.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Produto> findByCategoria(CategoriaProduto categoria);

    List<Produto> findByAtivoTrue();

    List<Produto> findByCategoriaAndAtivoTrue(CategoriaProduto categoria);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
