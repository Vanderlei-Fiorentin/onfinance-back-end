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
public class SaidaLanctoEntradaID implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "id_lancto")
    private LanctoContabilEntity lanctoEntrada;
    
    @ManyToOne
    @JoinColumn(name = "id_parcela")
    private ParcelaLanctoContabilEntity parcelaLanctoSaida;

    public SaidaLanctoEntradaID() {
    }

    public LanctoContabilEntity getLanctoEntrada() {
        return lanctoEntrada;
    }

    public void setLanctoEntrada(LanctoContabilEntity lanctoEntrada) {
        this.lanctoEntrada = lanctoEntrada;
    }

    public ParcelaLanctoContabilEntity getParcelaLanctoSaida() {
        return parcelaLanctoSaida;
    }

    public void setParcelaLanctoSaida(ParcelaLanctoContabilEntity parcelaLanctoSaida) {
        this.parcelaLanctoSaida = parcelaLanctoSaida;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.lanctoEntrada);
        hash = 59 * hash + Objects.hashCode(this.parcelaLanctoSaida);
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
        final SaidaLanctoEntradaID other = (SaidaLanctoEntradaID) obj;
        if (!Objects.equals(this.lanctoEntrada, other.lanctoEntrada)) {
            return false;
        }
        return Objects.equals(this.parcelaLanctoSaida, other.parcelaLanctoSaida);
    }
    
    
    
}
