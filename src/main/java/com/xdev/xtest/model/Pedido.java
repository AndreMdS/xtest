/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author X
 */

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    public Pedido() {
    }

    public Pedido(Long clienteId, Long enderecoId, Double valorTotal) {
        this.clienteId = clienteId;
        this.enderecoId = enderecoId;
        this.valorTotal = valorTotal;
    }
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long Id;
    
    @Column(name = "cliente_id")
    private Long clienteId;
    
    @Column(name = "endereco_id")
    private Long enderecoId;
    
    @Column(name = "valor_total")
    private Double valorTotal;

    
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" + "Id=" + Id + ", clienteId=" + clienteId + ", enderecoId=" + enderecoId + ", valorTotal=" + valorTotal + '}';
    }
    
}
