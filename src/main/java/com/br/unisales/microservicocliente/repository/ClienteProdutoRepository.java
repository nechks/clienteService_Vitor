package com.br.unisales.microservicocliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.unisales.microservicocliente.table.ClienteProduto;

/**
 * @apiNote Método responsável por realizar o crud na tabela cliente_produto
 * 
 * @author Vito Rodrigues Franzosi
 * @Data Criação 03.05.2024
 */
@Repository
public interface ClienteProdutoRepository extends JpaRepository<ClienteProduto, Integer> {
    List<ClienteProduto> findByClienteId(Integer idCliente);
    Optional<ClienteProduto> findByClienteIdAndIdProduto(Integer idCliente, Integer idProduto);
}
