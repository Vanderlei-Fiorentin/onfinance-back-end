package com.onfinance.dtos;

import com.onfinance.entities.PagamentoEntity;
import java.util.List;

public class DashboardDto {

    private List<PagamentoEntity> lanctosEntradaAVencer;
    private List<PagamentoEntity> lanctosSaidaAVencer;
    private List<PagamentoEntity> lanctosEntradaVencidos;
    private List<PagamentoEntity> lanctosSaidaVencidos;
    private List<PagamentoEntity> lanctosEntradaEmAbertoMesesAnteriores;
    private List<PagamentoEntity> lanctosSaidaEmAbertoMesesAnteriores;
    private double totalSaidasPagas;
    private double totalEntradasRecebidas;
    private double totalSaidasVencidas;
    private double totalEntradasVencidas;
    private double totalSaidasAVencer;
    private double totalEntradasAVencer;
    private double totalEntradasEmAbertoMesesAnteriores;
    private double totalSaidasEmAbertoMesesAnteriores;
    private double totalSaidasMesAnterior;
    private double totalEntradasMesAnterior;

    public DashboardDto() {

    }

    public List<PagamentoEntity> getLanctosEntradaAVencer() {
        return lanctosEntradaAVencer;
    }

    public void setLanctosEntradaAVencer(List<PagamentoEntity> lanctosEntradaAVencer) {
        this.lanctosEntradaAVencer = lanctosEntradaAVencer;
    }

    public List<PagamentoEntity> getLanctosSaidaAVencer() {
        return lanctosSaidaAVencer;
    }

    public void setLanctosSaidaAVencer(List<PagamentoEntity> lanctosSaidaAVencer) {
        this.lanctosSaidaAVencer = lanctosSaidaAVencer;
    }

    public List<PagamentoEntity> getLanctosEntradaVencidos() {
        return lanctosEntradaVencidos;
    }

    public void setLanctosEntradaVencidos(List<PagamentoEntity> lanctosEntradaVencidos) {
        this.lanctosEntradaVencidos = lanctosEntradaVencidos;
    }

    public List<PagamentoEntity> getLanctosSaidaVencidos() {
        return lanctosSaidaVencidos;
    }

    public void setLanctosSaidaVencidos(List<PagamentoEntity> lanctosSaidaVencidos) {
        this.lanctosSaidaVencidos = lanctosSaidaVencidos;
    }

    public double getTotalSaidasPagas() {
        return totalSaidasPagas;
    }

    public void setTotalSaidasPagas(double totalSaidasPagas) {
        this.totalSaidasPagas = totalSaidasPagas;
    }

    public double getTotalEntradasRecebidas() {
        return totalEntradasRecebidas;
    }

    public void setTotalEntradasRecebidas(double totalEntradasRecebidas) {
        this.totalEntradasRecebidas = totalEntradasRecebidas;
    }

    public double getTotalSaidasVencidas() {
        return totalSaidasVencidas;
    }

    public void setTotalSaidasVencidas(double totalSaidasVencidas) {
        this.totalSaidasVencidas = totalSaidasVencidas;
    }

    public double getTotalEntradasVencidas() {
        return totalEntradasVencidas;
    }

    public void setTotalEntradasVencidas(double totalEntradasVencidas) {
        this.totalEntradasVencidas = totalEntradasVencidas;
    }

    public double getTotalSaidasAVencer() {
        return totalSaidasAVencer;
    }

    public void setTotalSaidasAVencer(double totalSaidasAVencer) {
        this.totalSaidasAVencer = totalSaidasAVencer;
    }

    public double getTotalEntradasAVencer() {
        return totalEntradasAVencer;
    }

    public void setTotalEntradasAVencer(double totalEntradasAVencer) {
        this.totalEntradasAVencer = totalEntradasAVencer;
    }

    public double getTotalEntradasEmAbertoMesesAnteriores() {
        return totalEntradasEmAbertoMesesAnteriores;
    }

    public void setTotalEntradasEmAbertoMesesAnteriores(double totalEntradasEmAbertoMesesAnteriores) {
        this.totalEntradasEmAbertoMesesAnteriores = totalEntradasEmAbertoMesesAnteriores;
    }

    public double getTotalSaidasEmAbertoMesesAnteriores() {
        return totalSaidasEmAbertoMesesAnteriores;
    }

    public void setTotalSaidasEmAbertoMesesAnteriores(double totalSaidasEmAbertoMesesAnteriores) {
        this.totalSaidasEmAbertoMesesAnteriores = totalSaidasEmAbertoMesesAnteriores;
    }

    public List<PagamentoEntity> getLanctosEntradaEmAbertoMesesAnteriores() {
        return lanctosEntradaEmAbertoMesesAnteriores;
    }

    public void setLanctosEntradaEmAbertoMesesAnteriores(List<PagamentoEntity> lanctosEntradaEmAbertoMesesAnteriores) {
        this.lanctosEntradaEmAbertoMesesAnteriores = lanctosEntradaEmAbertoMesesAnteriores;
    }

    public List<PagamentoEntity> getLanctosSaidaEmAbertoMesesAnteriores() {
        return lanctosSaidaEmAbertoMesesAnteriores;
    }

    public void setLanctosSaidaEmAbertoMesesAnteriores(List<PagamentoEntity> lanctosSaidaEmAbertoMesesAnteriores) {
        this.lanctosSaidaEmAbertoMesesAnteriores = lanctosSaidaEmAbertoMesesAnteriores;
    }

    public double getTotalSaidasMesAnterior() {
        return totalSaidasMesAnterior;
    }

    public void setTotalSaidasMesAnterior(double totalSaidasMesAnterior) {
        this.totalSaidasMesAnterior = totalSaidasMesAnterior;
    }

    public double getTotalEntradasMesAnterior() {
        return totalEntradasMesAnterior;
    }

    public void setTotalEntradasMesAnterior(double totalEntradasMesAnterior) {
        this.totalEntradasMesAnterior = totalEntradasMesAnterior;
    }
}
