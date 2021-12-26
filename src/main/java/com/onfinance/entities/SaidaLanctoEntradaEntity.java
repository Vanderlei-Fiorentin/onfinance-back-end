package com.onfinance.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "saidas_lancto_entrada")
public class SaidaLanctoEntradaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    SaidaLanctoEntradaID saidaLanctoEntradaID;

    public SaidaLanctoEntradaEntity() {
    }

    public SaidaLanctoEntradaID getSaidaLanctoEntradaID() {
        return saidaLanctoEntradaID;
    }

    public void setSaidaLanctoEntradaID(SaidaLanctoEntradaID saidaLanctoEntradaID) {
        this.saidaLanctoEntradaID = saidaLanctoEntradaID;
    }

}