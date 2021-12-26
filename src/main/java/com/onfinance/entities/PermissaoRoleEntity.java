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
@Table(name = "permissoes_roles")
public class PermissaoRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PermissaoRoleID permissaoRoleID;

    public PermissaoRoleID getPermissaoRoleID() {
        return permissaoRoleID;
    }

    public void setPermissaoRoleID(PermissaoRoleID permissaoRoleID) {
        this.permissaoRoleID = permissaoRoleID;
    }

}