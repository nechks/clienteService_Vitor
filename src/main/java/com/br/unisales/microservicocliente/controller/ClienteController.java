package com.br.unisales.microservicocliente.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.unisales.microservicocliente.service.ClienteService;
import com.br.unisales.microservicocliente.service.CompararUUID;

/**
 * @apiNote Classe responsável por recever requisições do navegador (browser) e responder
 * 
 * @author Vito Rodrigues Franzosi
 * @Data Criação 26.04.2024
 */
@Controller
public class ClienteController {
    @Autowired
    private ClienteService servico;

    @Autowired
    private CompararUUID comparar;

    /**
     * @apiNote Método responsável por retornar a lista des clientes cadastrados no banco de dados
     * @return ResponseEntity<List<Cliente>>
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024
     */
    @PostMapping("/listarCliente")
    public ResponseEntity<String> listarCliente(@RequestParam("nome") String nome, @RequestParam("sexo") String sexo,
                                                @RequestParam("cpf") String cpf, @RequestParam("token") String token) {
        //this.servico.salvar(null, 2, "Francesco Campos Franzosi", "M", "658.342.879-79");
        //this.servico.salvar(null, 3, "Ana Carolina Silva", "F", "435.657.438-80");
        if(this.comparar.compararToken(UUID.fromString(token)))
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.listar(nome, sexo, cpf));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * @apiNote Método responsável por buscar o cliente pelo id do usuário
     * @param String token
     * @param Integer id
     * @return ResponseEntity<String>
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024
     */
    @PostMapping("/buscarClientePorIdUsuario")
    public ResponseEntity<String> buscarClientePorIdUsuario(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token)))
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.buscarPorIdUsuario(id));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * @apiNote Método responsável por buscar o cliente pelo seu id
     * @param Integer id
     * @param String token
     * @return ResponseEntity<String>
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024
     */
    @PostMapping("/buscarClientePorId")
    public ResponseEntity<String> buscarClientePorId(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token)))
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.buscarPorId(id));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    /**
     * @apiNote Método responsável por receber os dados do cliente para serem salvos
     * @param String token
     * @param Integer id
     * @param String nome
     * @param String sexo
     * @param String cpf
     * @param Integer idUsuario
     * @return ResponseEntity<String>
     *
     * @author Vito Rodrigues Franzosi
     * @Data Criação 01.05.2024
     */
    @PostMapping("salvarCliente")
    public ResponseEntity<String> salvarCliente(@RequestParam("id") Integer id, @RequestParam("nome") String nome,
                                                @RequestParam("sexo") String sexo, @RequestParam("cpf") String cpf,
                                                @RequestParam("id_usuario") Integer idUsuario, @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token))) 
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.salvar(id, idUsuario, nome, sexo, cpf));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * @apiNote Método responsável por receber o código do cliente de deverá ser excluído
     * @param Integer id
     * @param String token
     * @return ResponseEntity<String>
     *
     * @author Vito Rodrigues Franzosi
     * @Data Criação 03.05.2024
     */
    @PostMapping("/excluirCliente")
    public ResponseEntity<String> excluirCliente(@RequestParam("id") Integer id, @RequestParam("token") String token) {
        if(this.comparar.compararToken(UUID.fromString(token))) 
            return ResponseEntity.status(HttpStatus.OK).body(this.servico.excluir(id));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
