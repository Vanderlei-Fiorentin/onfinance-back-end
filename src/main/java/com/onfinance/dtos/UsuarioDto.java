package com.onfinance.dtos;

import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.entities.UsuarioRoleEntity;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UsuarioDto {

    private int id;
    private String nome;
    private String email;
    private boolean administrador;
    private String usuario;
    private String senha;
    private boolean ativo;
    private PerfilUsuarioEntity perfil;
    private List<UsuarioRoleEntity> roles;       
    
    public UsuarioDto(UsuarioEntity usuario, PerfilUsuarioEntity perfil, List<UsuarioRoleEntity> roles) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.usuario = usuario.getUsuario();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.administrador = usuario.isAdministrador();
        this.ativo = usuario.isAtivo();
        this.perfil= perfil;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public PerfilUsuarioEntity getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuarioEntity perfil) {
        this.perfil = perfil;
    }

    public List<UsuarioRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UsuarioRoleEntity> roles) {
        this.roles = roles;
    }

}
