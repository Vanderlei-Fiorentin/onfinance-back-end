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
public class PermissaoRoleID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_permissao")
    private PermissaoEntity permissao;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    public PermissaoEntity getPermissao() {
        return permissao;
    }

    public void setPermissao(PermissaoEntity permissao) {
        this.permissao = permissao;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

}