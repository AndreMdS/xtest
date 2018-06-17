/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.utils;

import org.springframework.stereotype.Component;

/**
 *
 * @author X
 */

@Component
public class MessageReceiver {
    
    public void receiveMessage(String message){
        System.out.println("< ----------- > Pedido Realizado com SUCESSO! < ----------- >");
        System.out.println(message);
        System.out.println("< ----------- > Fim de Mensagem < ----------- >\n");
    }

}
