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
 * @author Vanderlei Fiorentin
 */

@Embeddable
public class TransferenciaBancariaID implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "id_lancto_origem")
    private LanctoContabilEntity lanctoOrigem;
    
    @ManyToOne
    @JoinColumn(name = "id_lancto_destino")
    private LanctoContabilEntity lanctoDestino;

    public TransferenciaBancariaID() {
        
    }

    public LanctoContabilEntity getLanctoOrigem() {
        return lanctoOrigem;
    }

    public void setLanctoOrigem(LanctoContabilEntity lanctoOrigem) {
        this.lanctoOrigem = lanctoOrigem;
    }

    public LanctoContabilEntity getLanctoDestino() {
        return lanctoDestino;
    }

    public void setLanctoDestino(LanctoContabilEntity lanctoDestino) {
        this.lanctoDestino = lanctoDestino;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.lanctoOrigem);
        hash = 71 * hash + Objects.hashCode(this.lanctoDestino);
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
        final TransferenciaBancariaID other = (TransferenciaBancariaID) obj;
        if (!Objects.equals(this.lanctoOrigem, other.lanctoOrigem)) {
            return false;
        }
        return Objects.equals(this.lanctoDestino, other.lanctoDestino);
    }
    
}
