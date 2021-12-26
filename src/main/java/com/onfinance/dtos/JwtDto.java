package com.onfinance.dtos;

import com.onfinance.entities.PerfilUsuarioEntity;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class JwtDto {

    private PerfilUsuarioEntity perfil;
    private String jwt;

    public PerfilUsuarioEntity getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuarioEntity perfil) {
        this.perfil = perfil;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}