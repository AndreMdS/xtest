/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.utils;

import com.xdev.xtest.model.Endereco;
import com.xdev.xtest.model.Produto;
import com.xdev.xtest.repository.Enderecos;
import com.xdev.xtest.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author X
 */
@Component
public class PreLoadData implements ApplicationListener<ApplicationReadyEvent> {
    
    @Autowired
    private Produtos produtos;
    
    @Autowired
    private Enderecos enderecos;
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent e) {
        produtos.saveAndFlush(new Produto("Camiseta vermelha", (float) 50.20));
        produtos.saveAndFlush(new Produto("Camiseta azul", (float) 20.00));
        produtos.saveAndFlush(new Produto("Camiseta verde", (float) 35.80));
        produtos.saveAndFlush(new Produto("Calça jeans", (float) 45.50));
        produtos.saveAndFlush(new Produto("Calça esportiva", (float) 55.00));
        produtos.saveAndFlush(new Produto("Blusa masculina", (float) 100.00));
        produtos.saveAndFlush(new Produto("Blusa feminina", (float) 150.70));
        produtos.saveAndFlush(new Produto("Sapato", (float) 80.30));
        produtos.saveAndFlush(new Produto("Tenis", (float) 160.90));
        enderecos.saveAndFlush(new Endereco("Rua Bonita", 500, "", "Londrina", "Paraná", 86000078, 1L));
        enderecos.saveAndFlush(new Endereco("Rua Reta", 230, "Ap 85", "Cambé", "Paraná", 86000604, 2L));
    }
    
}
