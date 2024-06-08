package com.br.unisales.microservicocliente.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "nome", nullable = false, length = 150)
    private String nome;
    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;
    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    private String cpf;
}