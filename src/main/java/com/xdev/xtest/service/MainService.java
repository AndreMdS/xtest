/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xdev.xtest.XtestApp;
import com.xdev.xtest.model.*;
import com.xdev.xtest.repository.*;
import com.xdev.xtest.utils.JsonToObjetct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author X
 */
@Service
public class MainService {

    @Autowired
    private Enderecos enderecos;

    @Autowired
    private Pedidos pedidos;

    @Autowired
    private Produtos produtos;

    @Autowired
    private ProdutoPedidos produtoPedidos;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public ResponseEntity ensurePedido(String data) {
        int statusProdutos;
        int statusEndereco;
        int status = 201;
        String bodyResponse = "";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result;
        try {
            result = mapper.readTree(data);
        } catch (IOException ex) {
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return ResponseEntity.status(status).body("Payload Inválido!");
        }
        Pedido pedido = JsonToObjetct.extractPedido(result);
        Endereco endereco = JsonToObjetct.extractEndereco(result);
        ArrayList<ProdutoPedido> produtoList = JsonToObjetct.extractProdutos(result);
        if (!validatePedido(pedido)) {
            bodyResponse = bodyResponse + "Pedido não está correto!\n";
            status = 418;
        }
        statusProdutos = validateProdutos(produtoList, pedido.getValorTotal());
        if (statusProdutos == 0) {
            status = 418;
            bodyResponse = bodyResponse + "Sem produtos na lista!\n";
        } else if (statusProdutos == 1) {
            status = 418;
            bodyResponse = bodyResponse + "Preço total incoerente!\n";
        }
        statusEndereco = validateEndereco(endereco, pedido);
        if (statusEndereco == 0) {
            status = 418;
            bodyResponse = bodyResponse + "Endereço não está correto!\n";
        } else if (statusEndereco == 1) {
            status = 418;
            bodyResponse = bodyResponse + "Endereço não pertence ao cliente!\n";
        }
        if (status == 201) {
            savePedidoAndDependenciesAndSendMessage(pedido, endereco, produtoList);
            bodyResponse = "Pedido realizado!";
        }
        return ResponseEntity.status(status).body(bodyResponse);
    }

    private void savePedidoAndDependenciesAndSendMessage(Pedido pedido, Endereco endereco, ArrayList<ProdutoPedido> produtoList) {
        if (endereco != null) {
            enderecos.saveAndFlush(endereco);
            pedido.setEnderecoId(endereco.getId());
        }
        pedidos.saveAndFlush(pedido);
        for (ProdutoPedido produtoPedido : produtoList) {
            produtoPedido.setPedidoId(pedido.getId());
        }
        produtoPedidos.saveAll(produtoList);
        
        String message = pedido.toString() + " " + produtoList.toString();
        
        rabbitTemplate.convertAndSend(XtestApp.QUEUE_NAME, message);
    }

    private boolean validatePedido(Pedido pedido) {
        return pedido.getClienteId() != null && pedido.getValorTotal() != null;
    }

    private int validateEndereco(Endereco endereco, Pedido pedido) {
        if (pedido.getEnderecoId() == null && endereco.getLogradouro() != null &&
                endereco.getNumero() != null && endereco.getCidade() != null &&
                endereco.getEstado() != null && endereco.getCep() != null) {
            endereco.setClienteId(pedido.getClienteId());
            return 2;
        } else if (pedido.getEnderecoId() != null && endereco == null) {
            Long clienteId = enderecos.getOne(pedido.getEnderecoId()).getClienteId();
            if (clienteId == pedido.getClienteId()) {
                return 3;
            }
            return 1;
        }
        return 0;
    }

    private int validateProdutos(ArrayList<ProdutoPedido> produtoList, Double valorTotal) {
        if (produtoList.isEmpty()) {
            return 0;
        }
        Double totalCalculado = Double.valueOf(0);
        for (ProdutoPedido produtoPedido : produtoList) {
            Produto produto = produtos.getOne(produtoPedido.getProdutoId());
            totalCalculado =  totalCalculado + (produto.getValor() * produtoPedido.getQuantidade());
        }
        if (!verifyTotal(totalCalculado, valorTotal)) {
            return 1;
        }
        return 2;
    }
    
    private boolean verifyTotal(double totalCalculado, double valorTotal){
        totalCalculado = totalCalculado * 100;
        valorTotal = valorTotal * 100;
        int totalInt = (int) totalCalculado;
        int realInt = (int) valorTotal;
        return totalInt == realInt;
    }
    
}
