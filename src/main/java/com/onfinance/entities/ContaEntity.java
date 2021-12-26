package com.onfinance.entities;

import java.io.Serializable;
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
@Table(name = "contas_corrente")
public class ContaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_agencia")
    private AgenciaEntity agencia;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;
    
    @Column(name = "numero")
    private int numero;
    
    @Column(name = "digito_verificador")
    private int digitoVerificador;
    
    @Column(name = "saldo_anterior")
    private double saldoAnterior;
    
    @Column(name = "saldo")
    private double saldo;
    
    @Column(name = "cheque_especial")
    private double chequeEspecial;
    
    @Column(name = "ativo")
    private boolean ativo;

    public ContaEntity() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AgenciaEntity getAgencia() {
        return agencia;
    }

    public void setAgencia(AgenciaEntity agencia) {
        this.agencia = agencia;
    }
    
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
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

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
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
        final ContaEntity other = (ContaEntity) obj;
        return this.id == other.id;
    }
    
    
    
}