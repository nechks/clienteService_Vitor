package com.br.unisales.microservicocliente.service;

import java.nio.charset.Charset;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class CompararUUID {

    /**
     * @apiNote Método responsável por realizar a comparação dos tokens
     * @param UUID token
     * @return boolean
     *
     * @author Vito Rodrigues Franzosi
     * @Data Criação 30.04.2024
     */
    public boolean compararToken(UUID token) {
        String chave = "Sistem de micro servico login";
        Charset charset = Charset.forName("ASCII");
        byte[] byteArrray = chave.getBytes(charset);
        UUID uuid = UUID.nameUUIDFromBytes(byteArrray);
        if(uuid.compareTo(token)==0)
            return true;
        return false;
    }
}
