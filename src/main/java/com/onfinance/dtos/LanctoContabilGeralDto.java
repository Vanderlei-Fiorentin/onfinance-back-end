package com.onfinance.dtos;

import com.onfinance.entities.DocumentoLanctoContabilEntity;
import com.onfinance.entities.EventoLanctoEntradaEntity;
import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.entities.ProdutoLanctoSaidaEntity;
import com.onfinance.entities.SaidaLanctoEntradaEntity;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class LanctoContabilGeralDto {

    private LanctoContabilEntity lancto;
    private List<EventoLanctoEntradaEntity> eventos;
    private List<ProdutoLanctoSaidaEntity> produtos;
    private List<SaidaLanctoEntradaEntity> saidas;
    private List<DocumentoLanctoContabilEntity> documentos;

    public LanctoContabilGeralDto() {

    }

    public LanctoContabilEntity getLancto() {
        return lancto;
    }

    public void setLancto(LanctoContabilEntity lancto) {
        this.lancto = lancto;
    }

    public List<EventoLanctoEntradaEntity> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoLanctoEntradaEntity> eventos) {
        this.eventos = eventos;
    }

    public List<ProdutoLanctoSaidaEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoLanctoSaidaEntity> produtos) {
        this.produtos = produtos;
    }

    public List<SaidaLanctoEntradaEntity> getSaidas() {
        return saidas;
    }

    public void setSaidas(List<SaidaLanctoEntradaEntity> saidas) {
        this.saidas = saidas;
    }

    public List<DocumentoLanctoContabilEntity> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoLanctoContabilEntity> documentos) {
        this.documentos = documentos;
    }  

}
