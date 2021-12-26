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
public class UsuarioRoleID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
    
    public UsuarioRoleID() {
        
    }

    public UsuarioRoleID(UsuarioEntity usuario, RoleEntity role) {
        this.role = role;
        this.usuario = usuario;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

}
