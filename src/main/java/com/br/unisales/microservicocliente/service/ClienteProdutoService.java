package com.br.unisales.microservicocliente.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.unisales.microservicocliente.model.ClienteProdutoModel;
import com.br.unisales.microservicocliente.repository.ClienteProdutoRepository;
import com.br.unisales.microservicocliente.repository.ClienteRepository;
import com.br.unisales.microservicocliente.table.Cliente;
import com.br.unisales.microservicocliente.table.ClienteProduto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteProdutoService {
    @Autowired
    private ClienteProdutoRepository repo;
    @Autowired
    private ClienteRepository clienteRepo;

    public String listarProdutoDoCliente(Integer id) {
        try {
            List<ClienteProdutoModel> listaModelo = new ArrayList<ClienteProdutoModel>();
            List<ClienteProduto> lista = this.repo.findByClienteId(id);
            if((lista!=null) && (lista.size()>0)) {
                for(ClienteProduto item: lista)
                    listaModelo.add(this.converteClienteProdutoToModel(item));
            }
            return new ObjectMapper().writeValueAsString(listaModelo);
        } catch (Exception e) {
                System.err.println("Erro no método listarProdutoDoCliente() da classe ClienteProdutoService: "+e.getMessage());
                e.printStackTrace();
                JSONObject json = new JSONObject();
                json.put("erro", "Não foi possível encontrar o cliente");
                return json.toString();
        }
    }

    public String ativarInativarClienteProduto(Integer idCliente, Integer idProduto, Double desconto, int ativo) {
        JSONObject json = new JSONObject();
        try {
            ClienteProduto clienteProduto = new ClienteProduto();
            Optional<ClienteProduto> optional = this.repo.findByClienteIdAndIdProduto(idCliente, idProduto);
            if(optional.isPresent()) {
                clienteProduto = optional.get();
                clienteProduto.setAtivo(ativo);
                if(ativo==1)
                    clienteProduto.setDataAtivacao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                else
                    clienteProduto.setDataInativacao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                clienteProduto.setDescontoPercentual(desconto);
            } else {
                Optional<Cliente> cOptional = this.clienteRepo.findById(idCliente);
                if(cOptional.isPresent()) {
                    clienteProduto.setAtivo(ativo);
                    clienteProduto.setCliente(cOptional.get());
                    clienteProduto.setDataAtivacao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                    clienteProduto.setDescontoPercentual(desconto);
                    clienteProduto.setIdProduto(idProduto);
                }
            }
            if(clienteProduto.getCliente()!=null) {
                clienteProduto = this.repo.save(clienteProduto);
                json.put("id", clienteProduto.getId());
                return json.toString();
            }
            json.put("vazio", "Não foi possível salvar o produto do cliente");
            return json.toString();
        } catch (Exception e) {
            System.err.println("Erro no método ativarInativarClienteProduto() da classe ClienteProdutoService: "+e.getMessage());
            e.printStackTrace();
            json.put("erro", "Não foi possível salvar o produto do cliente");
            return json.toString();
        }
    }

    public String alterarDescontoClienteProduto(Integer idCliente, Integer idProduto, Double desconto) {
        JSONObject json = new JSONObject();
        try {
            ClienteProduto clienteProduto = new ClienteProduto();
            Optional<ClienteProduto> optional = this.repo.findByClienteIdAndIdProduto(idCliente, idProduto);
            if(optional.isPresent()) {
                clienteProduto = optional.get();
                if(clienteProduto.getAtivo()==1) {
                    clienteProduto.setDescontoPercentual(desconto);
                    clienteProduto = this.repo.save(clienteProduto);
                    json.put("id", clienteProduto.getId());
                    return json.toString();
                }
            }
            json.put("vazio", "Não foi possível alterar o desconto do produto do cliente");
            return json.toString();
        } catch (Exception e) {
            System.err.println("Erro no método alterarDescontoClienteProduto() da classe ClienteProdutoService: "+e.getMessage());
            e.printStackTrace();
            json.put("erro", "Não foi possível alterar o desconto do produto do cliente");
            return json.toString();
        }
    }

    private ClienteProdutoModel converteClienteProdutoToModel(ClienteProduto clienteProduto) {
        ClienteProdutoModel modelo = new ClienteProdutoModel();
        modelo.setAtivo(clienteProduto.getAtivo());
        modelo.setDataAtivacao(clienteProduto.getDataAtivacao());
        modelo.setDataInativacao(clienteProduto.getDataInativacao());
        modelo.setDescontoPercentual(clienteProduto.getDescontoPercentual());
        modelo.setId(clienteProduto.getId());
        modelo.setIdCliente(clienteProduto.getCliente().getId());
        modelo.setIdProduto(clienteProduto.getIdProduto());
        return modelo;
    }
}
