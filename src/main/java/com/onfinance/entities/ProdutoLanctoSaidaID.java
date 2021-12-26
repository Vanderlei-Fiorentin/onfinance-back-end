/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onfinance.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author vande
 */

@Embeddable
public class ProdutoLanctoSaidaID implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "id_lancto")
    private LanctoContabilEntity lanctoSaida;
    
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProdutoEntity produto;

    public LanctoContabilEntity getLanctoSaida() {
        return lanctoSaida;
    }

    public void setLanctoSaida(LanctoContabilEntity lanctoSaida) {
        this.lanctoSaida = lanctoSaida;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.lanctoSaida);
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
        final ProdutoLanctoSaidaID other = (ProdutoLanctoSaidaID) obj;
        return Objects.equals(this.lanctoSaida, other.lanctoSaida);
    }
        
}
