package com.br.unisales.microservicocliente.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ClienteProdutoModel {
    private Integer id;
    private Integer idCliente;
    private Integer idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private int ativo;
    private String dataAtivacao;
    private String dataInativacao;
    private Double descontoPercentual;
    private Double preco;
}
