/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.xdev.xtest.model.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author X
 */
public class JsonToObjetct {

    public static Pedido extractPedido(JsonNode data) {
        Pedido pedido = new Pedido();
        if (data.has("cliente")) {
            pedido.setClienteId(getLongOrNull("cliente", data));
        }
        if (data.has("valorTotal")) {
            pedido.setValorTotal(getDoubleOrNull("valorTotal", data));
        }
        if (data.has("enderecoId")) {
            pedido.setEnderecoId(getLongOrNull("enderecoId", data));
        }
        return pedido;
    }

    public static ArrayList<ProdutoPedido> extractProdutos(JsonNode data) {
        ArrayList<ProdutoPedido> produtoList = new ArrayList<>();
        if (data.has("produtos")) {
            JsonNode produtos = data.get("produtos");
            for (JsonNode produto : produtos) {
                ProdutoPedido produtoPedido = new ProdutoPedido();
                produtoPedido.setProdutoId(getLongOrNull("id", produto));
                produtoPedido.setQuantidade(getIntOrNull("quantidade", produto));
                produtoList.add(produtoPedido);
            }
        }
        return produtoList;
    }

    public static Endereco extractEndereco(JsonNode data) {
        Endereco endereco = null;
        JsonNode enderecoJson;
        if (data.has("endereco")) {
            endereco = new Endereco();
            enderecoJson = data.get("endereco");
            if (enderecoJson.has("logradouro")) {
                endereco.setLogradouro(getStringOrNull("logradouro", enderecoJson));
            }
            if (enderecoJson.has("numero")) {
                endereco.setNumero(getIntOrNull("numero", enderecoJson));
            }
            if (enderecoJson.has("complemento")) {
                endereco.setComplemento(getStringOrNull("complemento", enderecoJson));
            }
            if (enderecoJson.has("cidade")) {
                endereco.setCidade(getStringOrNull("cidade", enderecoJson));
            }
            if (enderecoJson.has("estado")) {
                endereco.setEstado(getStringOrNull("estado", enderecoJson));
            }
            if (enderecoJson.has("cep")) {
                endereco.setCep(formatCepToInt(getStringOrNull("cep", enderecoJson)));
            }
        }
        return endereco;
    }

    private static Integer formatCepToInt(String cep) {
        Integer cepInt = null;
        cep = cep.replaceAll("-", "");
        if (cep.length() < 8) {
            return null;
        }
        try {
            cepInt = Integer.parseInt(cep);
        } catch (NumberFormatException ex) {
            System.err.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
        return cepInt;
    }

    private static String getStringOrNull(String key, JsonNode node) {
        return node.has(key) ? node.get(key).asText() : null;
    }

    private static int getIntOrNull(String key, JsonNode node) {
        return node.has(key) ? node.get(key).asInt() : null;
    }

    private static Long getLongOrNull(String key, JsonNode node) {
        return node.has(key) ? node.get(key).asLong() : null;
    }

    private static double getDoubleOrNull(String key, JsonNode node) {
        return node.has(key) ? node.get(key).asDouble() : null;
    }

}
