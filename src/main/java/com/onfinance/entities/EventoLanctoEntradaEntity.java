
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
@Table(name = "eventos_lancto_contabil")
public class EventoLanctoEntradaEntity implements Serializable {

private static final long serialVersionUID = 1L;

    @EmbeddedId
    EventoLanctoEntradaID eventoLanctoEntradaID;

    @Column(name = "valor")
    private double valor;

    @Column(name = "quantidade")
    private int quantidade;

    public EventoLanctoEntradaEntity() {
    
    }

    public EventoLanctoEntradaID getEventoLanctoEntradaID() {
        return eventoLanctoEntradaID;
    }

    public void setEventoLanctoEntradaID(EventoLanctoEntradaID eventoLanctoEntradaID) {
        this.eventoLanctoEntradaID = eventoLanctoEntradaID;
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
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.eventoLanctoEntradaID);
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
        final EventoLanctoEntradaEntity other = (EventoLanctoEntradaEntity) obj;
        return Objects.equals(this.eventoLanctoEntradaID, other.eventoLanctoEntradaID);
    }  
    
    
}
