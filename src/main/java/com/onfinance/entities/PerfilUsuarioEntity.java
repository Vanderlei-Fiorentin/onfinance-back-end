package com.onfinance.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "perfis_usuarios")
public class PerfilUsuarioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @Column(name = "inscricao")
    private String inscricao;

    @Column(name = "dt_nascto")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataNascimento;

    @Column(name = "foto")
    private String foto;

    @Column(name = "aviso_fatura_vencida")
    private boolean avisoFaturaVencida;

    @Column(name = "aviso_fatura_avencer")
    private boolean avisoFaturaAVencer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isAvisoFaturaVencida() {
        return avisoFaturaVencida;
    }

    public void setAvisoFaturaVencida(boolean avisoFaturaVencida) {
        this.avisoFaturaVencida = avisoFaturaVencida;
    }

    public boolean isAvisoFaturaAVencer() {
        return avisoFaturaAVencer;
    }

    public void setAvisoFaturaAVencer(boolean avisoFaturaAVencer) {
        this.avisoFaturaAVencer = avisoFaturaAVencer;
    }
}
