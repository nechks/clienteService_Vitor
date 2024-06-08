package com.br.unisales.microservicocliente.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.unisales.microservicocliente.service.ClienteProdutoService;
import com.br.unisales.microservicocliente.service.CompararUUID;

@Controller
public class ClienteProdutoController {
    @Autowired
    private ClienteProdutoService servico;

    @Autowired
    private CompararUUID comparar;

    @PostMapping("/listarClienteProduto")
    public ResponseEntity<String> listarClienteProduto(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token)))
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.listarProdutoDoCliente(id));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/ativarInativarClienteProduto")
    public ResponseEntity<String> ativarInativarClienteProduto(@RequestParam("idCliente") Integer idCliente,
                                                               @RequestParam("idProduto") Integer idProduto,
                                                               @RequestParam("desconto") Double desconto,
                                                               @RequestParam("ativo") int ativo,
                                                               @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token)))
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.ativarInativarClienteProduto(idCliente, idProduto, desconto, ativo));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);                                                                
    }

    @PostMapping("/alterarDescontoClienteProduto")
    public ResponseEntity<String> alterarDescontoClienteProduto(@RequestParam("idCliente") Integer idCliente,
                                                               @RequestParam("idProduto") Integer idProduto,
                                                               @RequestParam("desconto") Double desconto,
                                                               @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token)))
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.alterarDescontoClienteProduto(idCliente, idProduto, desconto));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);                                                                
    }
}
