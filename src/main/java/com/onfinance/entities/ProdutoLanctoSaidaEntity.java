/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onfinance.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */

@Entity
@Table(name = "produtos_lancto_contabil")
public class ProdutoLanctoSaidaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    ProdutoLanctoSaidaID produtoLanctoSaidaID;
    
    @Column(name = "valor")
    private double valor;
    
    @Column(name = "quantidade")
    private int quantidade;

    public ProdutoLanctoSaidaEntity() {
    }

    public ProdutoLanctoSaidaID getProdutoLanctoSaidaID() {
        return produtoLanctoSaidaID;
    }

    public void setProdutoLanctoSaidaID(ProdutoLanctoSaidaID produtoLanctoSaidaID) {
        this.produtoLanctoSaidaID = produtoLanctoSaidaID;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.produtoLanctoSaidaID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProdutoLanctoSaidaEntity other = (ProdutoLanctoSaidaEntity) obj;
        return Objects.equals(this.produtoLanctoSaidaID, other.produtoLanctoSaidaID);
    }
    
    
    
}
