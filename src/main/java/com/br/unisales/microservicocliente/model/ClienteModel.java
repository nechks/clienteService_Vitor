package com.br.unisales.microservicocliente.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
    private Integer id;
    private Integer idUsuario;
    private String nome;
    private String sexo;
    private String cpf;
}