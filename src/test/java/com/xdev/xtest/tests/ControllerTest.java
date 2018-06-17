/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xdev.xtest.utils.JsonToObjetct;
import com.xdev.xtest.model.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author X
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void jsonExtractorPedidoTest() throws IOException {
        Pedido pedido = new Pedido((long) 5, (long) 10, 50.50);
        String data = PEDIDO_JSON_TO_EXTRACT;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(data);
        Assert.assertEquals(JsonToObjetct.extractPedido(result).toString(), pedido.toString());
    }

    @Test
    public void jsonExtractorEnderecoTest() throws IOException {
        Endereco endereco = new Endereco("Rua Grande", 100, "", "Londrina", "Paraná", 86047481, null);
        String data = ENDERECO_JSON_TO_EXTRACT;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(data);
        Assert.assertEquals(JsonToObjetct.extractEndereco(result).toString(), endereco.toString());
    }

    @Test
    public void jsonExtractorProdutosTest() throws IOException {
        ArrayList<ProdutoPedido> produtosList = new ArrayList<>();
        produtosList.add(new ProdutoPedido(null, (long) 7, 2));
        produtosList.add(new ProdutoPedido(null, (long) 18, 1));
        String data = PRODUTOS_JSON_TO_EXTRACT;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(data);
        Assert.assertEquals(JsonToObjetct.extractProdutos(result).toString(), produtosList.toString());
    }

    @Test
    public void postRequestCorrectTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pedidos")
                .contentType("application/json")
                .content(CORRECT_JSON_TO_REQUEST))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(), "Pedido realizado!");
    }
    
    @Test
    public void postRequestIncorrectTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pedidos")
                .contentType("application/json")
                .content(INCORRECT_JSON_TO_REQUEST))
                .andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(), "Endereço não pertence ao cliente!\n");
    }
    
    @Test
    public void postRequestCorrectTestNewEndereco() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pedidos")
                .contentType("application/json")
                .content(CORRECT_JSON_TO_REQUEST_NEW_ENDERECO))
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 201);
    }

    private static final String PEDIDO_JSON_TO_EXTRACT = "{\"cliente\":5,\"enderecoId\":10,\"valorTotal\":50.50}";

    private static final String ENDERECO_JSON_TO_EXTRACT = "{\"endereco\":{\"logradouro\":\"Rua Grande\",\"numero\""
            + ":100,\"complemento\":\"\",\"cidade\":\"Londrina\",\"estado\":\"Paraná\",\"cep\":\"86047-481\"}}";

    private static final String PRODUTOS_JSON_TO_EXTRACT = "{\"produtos\":[{\"id\":7,\"quantidade\":2},{\"id\":18,\""
            + "quantidade\":1}]}";

    private static final String CORRECT_JSON_TO_REQUEST = "{\"cliente\":1,\"valorTotal\":20.0,\"enderecoId\":1,\"produtos\""
            + ":[{\"id\":2,\"quantidade\":1}]}";
    
    private static final String INCORRECT_JSON_TO_REQUEST = "{\"cliente\":2,\"valorTotal\":20.0,\"enderecoId\":1,\"produtos\""
            + ":[{\"id\":2,\"quantidade\":1}]}";
    
    private static final String CORRECT_JSON_TO_REQUEST_NEW_ENDERECO = "{\"cliente\":10,\"valorTotal\":70.2,\"endereco\":"
            + "{\"logradouro\":\"Rua Zé\",\"numero\":100,\"complemento\":\"\",\"cidade\":\"Teste\",\"estado\""
            + ":\"Opa\",\"cep\":\"86045-804\"},\"produtos\":[{\"id\":2,\"quantidade\":1},{\"id\":1,\"quantidade\":1}]}";

}
