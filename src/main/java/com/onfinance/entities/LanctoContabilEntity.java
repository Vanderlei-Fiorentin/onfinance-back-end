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
@Table(name = "lanctos_contabeis")
public class LanctoContabilEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    public static final String AVISTA = "A";
    public static final String TRANSFERENCIA = "T";
    public static final String PARCELADO = "P";
    public static final String FIXO = "F";
    public static final String SAIDA = "S";
    public static final String ENTRADA = "E";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancto")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private EmpresaEntity empresa;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_lancto_ent")
    private LanctoContabilEntity lanctoEntrada;

    @ManyToOne
    @JoinColumn(name = "id_cartao")
    private CartaoCreditoEntity cartao;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private ContaEntity conta;
    
    @ManyToOne
    @JoinColumn(name = "id_conta_destino")
    private ContaEntity contaDestino;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_lancto")
    private LocalDate dataLancto;

    @Column(name = "tipo_lancto")
    private String tipoLancto;

    @Column(name = "tipo_pagto")
    private String tipoPagto;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "parcelas")
    private int parcelas;

    @Column(name = "inicio_vigencia")
    private int inicioVigencia;

    @Column(name = "valor")
    private double valor;

    @Column(name = "juros")
    private double juros;

    @Column(name = "valor_entrada")
    private double valorEntrada;

    @Column(name = "dia_pagto")
    private int diaPagamento;

    @Column(name = "descricao")
    private String descricao;

    public LanctoContabilEntity() {

    }
    
    @Override public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() {
        return (id == 0) ? null : id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmpresaEntity getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public LanctoContabilEntity getLanctoEntrada() {
        return lanctoEntrada;
    }

    public void setLanctoEntrada(LanctoContabilEntity lanctoEntrada) {
        this.lanctoEntrada = lanctoEntrada;
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
    
    public ContaEntity getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(ContaEntity contaDestino) {
        this.contaDestino = contaDestino;
    }

    public LocalDate getDataLancto() {
        return dataLancto;
    }

    public void setDataLancto(LocalDate dataLancto) {
        this.dataLancto = dataLancto;
    }

    public String getTipoLancto() {
        return tipoLancto;
    }

    public void setTipoLancto(String tipoLancto) {
        this.tipoLancto = tipoLancto;
    }

    public String getTipoPagto() {
        return tipoPagto;
    }

    public void setTipoPagto(String tipoPagto) {
        this.tipoPagto = tipoPagto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public int getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(int inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
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

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(int diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        final LanctoContabilEntity other = (LanctoContabilEntity) obj;
        return this.id == other.id;
    }
    
    

}