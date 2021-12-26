package com.onfinance.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.onfinance.entities.ParcelaLanctoContabilEntity;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class RenegociacaoLanctoContabilDto {

    private List<ParcelaLanctoContabilEntity> parcelas;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate dataRenegociacao;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate vencimento;
    private String tipoPagto;
    private Double juros;
    private Double multa;
    private Double desconto;
    private Double valorEntrada;
    private int qtdParcelas;
    private int diaPagamento;
    private int inicioVigencia;

    public int getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(int inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public List<ParcelaLanctoContabilEntity> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelaLanctoContabilEntity> parcelas) {
        this.parcelas = parcelas;
    }

    public LocalDate getDataRenegociacao() {
        return dataRenegociacao;
    }

    public void setDataRenegociacao(LocalDate dataRenegociacao) {
        this.dataRenegociacao = dataRenegociacao;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getTipoPagto() {
        return tipoPagto;
    }

    public void setTipoPagto(String tipoPagto) {
        this.tipoPagto = tipoPagto;
    }

    public Double getJuros() {
        return juros;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public int getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(int diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

}
