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
 * @author A687747
 */

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    public Produto() {
    }

    public Produto(String nome, Float valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Produto(Long Id, String nome, Float valor) {
        this.Id = Id;
        this.nome = nome;
        this.valor = valor;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long Id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "valor")
    private Float valor;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Produto{" + "Id=" + Id + ", nome=" + nome + ", valor=" + valor + '}';
    }
    
}
