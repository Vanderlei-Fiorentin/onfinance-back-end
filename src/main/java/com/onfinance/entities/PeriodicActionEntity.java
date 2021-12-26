package com.onfinance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "periodic_actions")
public class PeriodicActionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodic")
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "intervalo")
    private long intervalo;

    @Column(name = "delay")
    private long delay;

    @Column(name = "ultima_execucao")
    private LocalDateTime ultimaExecucao;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "quantidade_maxima_execucoes")
    private int quantidadeMaximaExecucao;

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

    public long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public LocalDateTime getUltimaExecucao() {
        return ultimaExecucao;
    }

    public void setUltimaExecucao(LocalDateTime ultimaExecucao) {
        this.ultimaExecucao = ultimaExecucao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getQuantidadeMaximaExecucao() {
        return quantidadeMaximaExecucao;
    }

    public void setQuantidadeMaximaExecucao(int quantidadeMaximaExecucao) {
        this.quantidadeMaximaExecucao = quantidadeMaximaExecucao;
    }

}
