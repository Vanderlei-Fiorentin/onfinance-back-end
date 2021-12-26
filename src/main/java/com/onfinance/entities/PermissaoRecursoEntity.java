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
@Table(name = "permissoes_recurso")
public class PermissaoRecursoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PermissaoRecursoID permissaoRecursoID;

    public PermissaoRecursoID getPermissaoRecursoID() {
        return permissaoRecursoID;
    }

    public void setPermissaoRecursoID(PermissaoRecursoID permissaoRecursoID) {
        this.permissaoRecursoID = permissaoRecursoID;
    }

}