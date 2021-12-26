package com.onfinance.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Embeddable
public class PermissaoRecursoID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_permissao")
    private PermissaoEntity permissao;

    @ManyToOne
    @JoinColumn(name = "id_recurso")
    private RecursoEntity recurso;

    public PermissaoEntity getPermissao() {
        return permissao;
    }

    public void setPermissao(PermissaoEntity permissao) {
        this.permissao = permissao;
    }

    public RecursoEntity getRecurso() {
        return recurso;
    }

    public void setRecurso(RecursoEntity recurso) {
        this.recurso = recurso;
    }

}