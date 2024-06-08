package com.br.unisales.microservicocliente.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "cliente_produto")
public class ClienteProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;
    @ManyToOne(targetEntity=Cliente.class, fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
    @Column(name = "id_produto", nullable = false)
    private Integer idProduto;
    @Column(name = "ativo", nullable = false)
    private int ativo;
    @Column(name = "data_ativacao", nullable = false)
    private String dataAtivacao;
    @Column(name = "data_inativacao")
    private String dataInativacao;
    @Column(name = "desconto_percentual")
    private Double descontoPercentual;
}
