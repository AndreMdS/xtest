/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xdev.xtest.repository;

import com.xdev.xtest.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author A687747
 */
public interface Enderecos extends JpaRepository<Endereco, Long> {
    
}
