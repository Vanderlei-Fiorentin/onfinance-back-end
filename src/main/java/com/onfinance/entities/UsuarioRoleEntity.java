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
@Table(name = "usuarios_roles")
public class UsuarioRoleEntity implements Serializable {

    @EmbeddedId
    private UsuarioRoleID usuarioRoleID;
    
    public UsuarioRoleEntity() {
        
    }
    
    public UsuarioRoleEntity(UsuarioEntity usuario, RoleEntity role) {
        this.usuarioRoleID = new UsuarioRoleID(usuario, role);
    }

    public UsuarioRoleID getUsuarioRoleID() {
        return usuarioRoleID;
    }

    public void setUsuarioRoleID(UsuarioRoleID usuarioRoleID) {
        this.usuarioRoleID = usuarioRoleID;
    }

}