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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "cartoes_credito")
public class CartaoCreditoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartao")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_bandeira")
    private BandeiraEntity bandeira;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private ContaEntity conta;

    @Column(name = "numero")
    private long numero;

    @Column(name = "limite")
    private double limite;

    @Column(name = "limite_utilizado")
    private double limiteUtilizado;

    @Column(name = "dia_vencto")
    private int diaVencto;

    @Column(name = "fechamento")
    private int fechamento;
    
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "validade")
    private LocalDate validade;

    @Column(name = "debito_automatico")
    private boolean debitoAutomatico;
    
    @Column(name = "ativo")
    private boolean ativo;

    public CartaoCreditoEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BandeiraEntity getBandeira() {
        return bandeira;
    }

    public void setBandeira(BandeiraEntity bandeira) {
        this.bandeira = bandeira;
    }

    public ContaEntity getConta() {
        return conta;
    }

    public void setConta(ContaEntity conta) {
        this.conta = conta;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getLimiteUtilizado() {
        return limiteUtilizado;
    }

    public void setLimiteUtilizado(double limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    public int getDiaVencto() {
        return diaVencto;
    }

    public void setDiaVencto(int diaVencto) {
        this.diaVencto = diaVencto;
    }

    public int getFechamento() {
        return fechamento;
    }

    public void setFechamento(int fechamento) {
        this.fechamento = fechamento;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public boolean isDebitoAutomatico() {
        return debitoAutomatico;
    }

    public void setDebitoAutomatico(boolean debitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
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
        hash = 89 * hash + this.id;
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
        final CartaoCreditoEntity other = (CartaoCreditoEntity) obj;
        return this.id == other.id;
    }
    
    

}