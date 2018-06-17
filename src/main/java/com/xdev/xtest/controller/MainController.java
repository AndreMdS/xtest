/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.controller;

import com.xdev.xtest.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author X
 */

@Controller
public class MainController {
    
    @Autowired
    private MainService mainService;
    
    @PostMapping(path = "/api/pedidos", consumes = "application/json")
    public ResponseEntity saveAndSendPedido(@RequestBody String data){
        return mainService.ensurePedido(data);
    }
    
}
