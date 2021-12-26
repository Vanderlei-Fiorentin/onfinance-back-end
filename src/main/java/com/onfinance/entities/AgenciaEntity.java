/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onfinance.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "agencias")
public class AgenciaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agencia")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_banco")
    private BancoEntity banco;
    
    @Column(name = "numero")
    private int numero;
    
    @Column(name = "digito_verificador")
    private int digitoVerificador;

    public AgenciaEntity() {
    }

    public AgenciaEntity(int id, BancoEntity banco, int numero, int digitoVerificador) {
        this.id = id;
        this.banco = banco;
        this.numero = numero;
        this.digitoVerificador = digitoVerificador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BancoEntity getBanco() {
        return banco;
    }

    public void setBanco(BancoEntity banco) {
        this.banco = banco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(int digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgenciaEntity other = (AgenciaEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }
       
}
