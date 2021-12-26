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
@Table(name = "parcelas_lancto_contabil")
public class ParcelaLanctoContabilEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcela")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_lancto")
    private LanctoContabilEntity lanctoContabil;

    @ManyToOne
    @JoinColumn(name = "id_fatura")
    private FaturaEntity fatura;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "vencimento")
    private LocalDate vencimento;

    @Column(name = "parcela")
    private int parcela;

    @Column(name = "valor")
    private double valor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LanctoContabilEntity getLanctoContabil() {
        return lanctoContabil;
    }

    public void setLanctoContabil(LanctoContabilEntity lanctoContabil) {
        this.lanctoContabil = lanctoContabil;
    }

    public FaturaEntity getFatura() {
        return fatura;
    }

    public void setFatura(FaturaEntity fatura) {
        this.fatura = fatura;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ParcelaLanctoContabilEntity() {

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final ParcelaLanctoContabilEntity other = (ParcelaLanctoContabilEntity) obj;
        return this.id == other.id;
    }   
    

}