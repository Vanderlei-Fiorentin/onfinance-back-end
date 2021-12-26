
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
public class EventoLanctoEntradaID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_lancto")
    private LanctoContabilEntity lanctoEntrada;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private EventoEntity evento;

    public EventoLanctoEntradaID() {
    }

    public LanctoContabilEntity getLanctoEntrada() {
        return lanctoEntrada;
    }

    public void setLanctoEntrada(LanctoContabilEntity lanctoEntrada) {
        this.lanctoEntrada = lanctoEntrada;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.lanctoEntrada);
        hash = 59 * hash + Objects.hashCode(this.evento);
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
        final EventoLanctoEntradaID other = (EventoLanctoEntradaID) obj;
        if (!Objects.equals(this.lanctoEntrada, other.lanctoEntrada)) {
            return false;
        }
        return Objects.equals(this.evento, other.evento);
    }   
    
    
}
