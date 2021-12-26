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
@Table(name = "transferencias_bancarias")
public class TransferenciaBancariaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    TransferenciaBancariaID transferenciaBancariaID;

    public TransferenciaBancariaID getTransferenciaBancariaID() {
        return transferenciaBancariaID;
    }

    public void setTransferenciaBancariaID(TransferenciaBancariaID transferenciaBancariaID) {
        this.transferenciaBancariaID = transferenciaBancariaID;
    }

}