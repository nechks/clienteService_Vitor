package com.br.unisales.microservicocliente.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.unisales.microservicocliente.model.ClienteModel;
import com.br.unisales.microservicocliente.repository.ClienteProdutoRepository;
import com.br.unisales.microservicocliente.repository.ClienteRepository;
import com.br.unisales.microservicocliente.table.Cliente;
import com.br.unisales.microservicocliente.table.ClienteProduto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @apiNote Classe responsável por realizar os serviços solicitados pelo controllee
 * 
 * @author Vito Rodrigues Franzosi
 * @Data Criação 26.04.2024
 */
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private ClienteProdutoRepository clienteProdutoRepo;

    /**
     * @apiNote Método responsável por listar os clientes cadastrados no banco de dados
     * @return List<Cliente>
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024 / 03.05.2024
     */
    public String listar(String nome, String sexo, String cpf) {
        //this.repo.save(new Cliente(null, 2, "Francesco", "M", "435.879.435-67"));
        List<Cliente> lista = new ArrayList<Cliente>();
        List<ClienteModel> listaModelo = new ArrayList<ClienteModel>();
        if((cpf!=null) && (cpf.length()==14)) {
            Optional<Cliente> cliente = this.repo.findByCpf(cpf);
            if(cliente.isPresent())
                lista.add(cliente.get());
        } else if((nome!=null) && ((nome.trim()).length()>0)) {
            if(sexo!=null)
                lista=this.repo.findByNomeIgnoreCaseContainingAndSexo(nome, sexo);
            else
                lista=this.repo.findByNomeIgnoreCaseContaining(nome);
        } else if((sexo!=null) && ((sexo.trim()).length()>0))
            lista=this.repo.findBySexo(sexo);
        else
            lista=this.repo.findAll();
        if((lista!=null) && (lista.size()>0)) {
            for(Cliente item: lista)
                listaModelo.add(this.converterClienteToModel(item));
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(listaModelo);
        } catch (Exception e) {
            System.err.println("Erro no método listar() da classe ClienteService: "+e.getMessage());
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("erro", "Não foi possível encontrar o cliente");
            return json.toString();
        }
    }

    /**
     * @apiNote Método responsável por buscar o cliente cadastrados no banco de dados pelo seu id
     * @param Integer id
     * @return Cliente
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024
     */
    public String buscarPorId(Integer id) {
        Optional<Cliente> cliente = this.repo.findById(id);
        List<ClienteModel> listaModelo = new ArrayList<ClienteModel>();
        if(cliente.isPresent()) {
            listaModelo.add(this.converterClienteToModel(cliente.get()));
            try {
                return new ObjectMapper().writeValueAsString(listaModelo);
            } catch (Exception e) {
                System.err.println("Erro no método buscarClientePorIdUsuario() da classe ClienteService: "+e.getMessage());
                e.printStackTrace();
                JSONObject json = new JSONObject();
                json.put("erro", "Não foi possível encontrar o cliente");
                return json.toString();
            }
        }
        return null;
    }

    /**
     * @apiNote Método responsável por buscar o cliente cadastrados no banco de dados pelo código do seu usuário (id_usuario)
     * @param Integer idUsuario
     * @return String
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024
     */
    public String buscarPorIdUsuario(Integer idUsuario) {
        Optional<Cliente> cliente = this.repo.findByIdUsuario(idUsuario);
        List<ClienteModel> listaModelo = new ArrayList<ClienteModel>();
        if(cliente.isPresent())
            listaModelo.add(this.converterClienteToModel(cliente.get()));
        try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(listaModelo);
        } catch (Exception e) {
                System.err.println("Erro no método buscarClientePorIdUsuario() da classe ClienteService: "+e.getMessage());
                e.printStackTrace();
                JSONObject json = new JSONObject();
                json.put("erro", "Não foi possível encontrar o cliente");
                return json.toString();
        }
    }

    /**
     * @apiNote Método responsável por salvar os dados do cliente no banco de dados 
     * @param Integer id
     * @param Integer idUsuario
     * @param String nome
     * @param String sexo
     * @param String cpf
     * @return String
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 03.05.2024
     */
    public String salvar(Integer id, Integer idUsuario, String nome, String sexo, String cpf) {
        Integer idCliente=null;
        if((id!=null) && (id!=0))
            idCliente=id;
        Cliente cliente = new Cliente(idCliente, idUsuario, nome, sexo, cpf);
        ClienteModel modelo = this.converterClienteToModel(this.repo.save(cliente));
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(modelo);
        } catch (Exception e) {
            System.err.println("Erro no método salvar() da classe ClienteService: "+e.getMessage());
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("erro", "Não foi possível salvar os dados do cliente");
            return json.toString();
        }
    }

    /**
     * @apiNote Método responsável por excluir o cliente do banco de dados
     * @param Integer id
     * @return String
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 03.05.2024
     */
    public String excluir(Integer id) {
        Optional<Cliente> cliente = this.repo.findById(id);
        if(cliente.isPresent()) {
            List<ClienteProduto> listaClienteProduto = this.clienteProdutoRepo.findByClienteId(id);
            for(ClienteProduto item: listaClienteProduto)
                this.clienteProdutoRepo.delete(item);
            this.repo.delete(cliente.get());
            return "sucesso";
        }
        return "erro";
    }

    /**
     * @apiNote Método responsável por converter um cliente para o seu modelo
     * @param cliente
     * @return ClienteModel
     * 
     * @author Vito Rodrigues Franzosi
     * @Data Criação 26.04.2024
     */
    private ClienteModel converterClienteToModel(Cliente cliente) {
        return new ClienteModel(cliente.getId(), cliente.getIdUsuario(), cliente.getNome(), cliente.getSexo(), cliente.getCpf());
    }
}
