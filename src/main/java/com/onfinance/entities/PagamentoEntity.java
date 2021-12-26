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
@Table(name = "pagamentos")
public class PagamentoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_fatura")
    private FaturaEntity fatura;

    @ManyToOne
    @JoinColumn(name = "id_cartao")
    private CartaoCreditoEntity cartao;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private ContaEntity conta;

    @Column(name = "valor")
    private double valor;

    @Column(name = "juros")
    private double juros;

    @Column(name = "multa")
    private double multa;

    @Column(name = "desconto")
    private double desconto;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_pagto")
    private LocalDate dataPagamento;

    @Column(name = "tipo_pagto")
    private char tipoPagamento;
    
    public PagamentoEntity() {
        
    }

    public PagamentoEntity(FaturaEntity fatura, CartaoCreditoEntity cartao, ContaEntity conta, double valor, double juros, double multa, double desconto, char tipoPagamento) {
        this.fatura = fatura;
        this.cartao = cartao;
        this.conta = conta;
        this.valor = valor;
        this.juros = juros;
        this.multa = multa;
        this.desconto = desconto;
        this.tipoPagamento = tipoPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FaturaEntity getFatura() {
        return fatura;
    }

    public void setFatura(FaturaEntity fatura) {
        this.fatura = fatura;
    }

    public CartaoCreditoEntity getCartao() {
        return cartao;
    }

    public void setCartao(CartaoCreditoEntity cartao) {
        this.cartao = cartao;
    }

    public ContaEntity getConta() {
        return conta;
    }

    public void setConta(ContaEntity conta) {
        this.conta = conta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public char getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(char tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

}