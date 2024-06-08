package com.br.unisales.microservicocliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.unisales.microservicocliente.table.Cliente;

/**
 * @apiNote Classe responsável por realizar o CRUD na tabela cliente do banco de dados
 * 
 * @author Vito Rodrigues Franzosi
 * @Data Criação 26.04.2024
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByIdUsuario(Integer idUsuario);
    Optional<Cliente> findByCpf(String cpf);
    List<Cliente> findByNomeIgnoreCaseContainingAndSexo(String nome, String sexo);
    List<Cliente> findByNomeIgnoreCaseContaining(String nome);
    List<Cliente> findBySexo(String sexo);
}
