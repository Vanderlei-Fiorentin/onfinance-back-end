package com.onfinance.entities;

import com.onfinance.periodics.TentativaExecucaoPeriodicAction;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "documentos_lancto_contabil")
public class DocumentoLanctoContabilEntity extends TentativaExecucaoPeriodicAction {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_lancto")
    private LanctoContabilEntity lancto;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "documento")
    private String documento;
    
    @Column(name = "upload")
    private boolean upload;
    
    @Column(name = "numero_tentativas_realizadas")
    private int numeroTentativasRealizadas;

    @Column(name = "data_proxima_execucao")
    private LocalDateTime dataProximaExecucao;    

    public DocumentoLanctoContabilEntity() {
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LanctoContabilEntity getLancto() {
        return lancto;
    }

    public void setLancto(LanctoContabilEntity lancto) {
        this.lancto = lancto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }
    
    @Override
    public int getNumeroTentativasRealizadas() {
        return numeroTentativasRealizadas;
    }

    @Override
    public void setNumeroTentativasRealizadas(int numeroTentativasRealizadas) {
        this.numeroTentativasRealizadas = numeroTentativasRealizadas;
    }

    @Override
    public LocalDateTime getDataProximaExecucao() {
        return dataProximaExecucao;
    }

    @Override
    public void setDataProximaExecucao(LocalDateTime dataProximaExecucao) {
        this.dataProximaExecucao = dataProximaExecucao;
    }

    @Override
    public void incrementarNumeroTentativasRealizadas() {
        numeroTentativasRealizadas += 1;
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
        final DocumentoLanctoContabilEntity other = (DocumentoLanctoContabilEntity) obj;
        return this.id == other.id;
    }    
   
}