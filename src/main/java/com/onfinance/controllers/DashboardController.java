package com.onfinance.controllers;

import com.onfinance.dtos.DashboardDto;
import com.onfinance.entities.PagamentoEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.dtos.GraficoDto;
import com.onfinance.repositories.PagamentoRepository;
import com.onfinance.repositories.ProdutoLanctoSaidaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class DashboardController {

    public DashboardController() {

    }

    public DashboardDto getReceitasDespesasByCompetencia(LocalDate inicio, LocalDate fim) throws Exception {

        DashboardDto dashboard = new DashboardDto();
        double totalEntradasAVencer = 0;
        double totalEntradasVencidas = 0;
        double totalEntradasPagas = 0;
        double totalEntradasEmAbertoMesesAnteriores = 0;
        double totalSaidasAVencer = 0;
        double totalSaidasVencidas = 0;
        double totalSaidasPagas = 0;
        double totalSaidasEmAbertoMesesAnteriores = 0;
        double totalEntradasMesAnterior = 0;
        double totalSaidasMesAnterior = 0;
        List<PagamentoEntity> entradasPagas;
        List<PagamentoEntity> entradasAVencer;
        List<PagamentoEntity> entradasVencidas;
        List<PagamentoEntity> entradasEmAbertomesesAnteriores;
        List<PagamentoEntity> saidasPagas;
        List<PagamentoEntity> saidasAVencer;
        List<PagamentoEntity> saidasVencidas;
        List<PagamentoEntity> saidasEmAbertomesesAnteriores;
        LocalDate marcoInicial = LocalDate.of(1900, 1, 1);

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            PagamentoRepository entradaRepository = session.get(PagamentoRepository.class);
            entradasPagas = entradaRepository.findPagamentosByFiltro(inicio, fim, "P", "E", List.of("TD"), 0, 0);
            entradasAVencer = entradaRepository.findPagamentosByFiltro(inicio, fim, "A", "E",List.of("TD"), 0, 0);
            entradasVencidas = entradaRepository.findPagamentosByFiltro(inicio, fim, "V", "E", List.of("TD"), 0, 0);
            entradasEmAbertomesesAnteriores = entradaRepository.findPagamentosByFiltro(marcoInicial, inicio.minusDays(1), "V", "E", List.of("TD"), 0, 0);
            totalEntradasMesAnterior = entradaRepository.findTotalPagamentos(inicio.minusMonths(1), fim.minusMonths(1), "TD", "E", List.of("TD"), 0, 0);
        }

        for (PagamentoEntity fatura : entradasPagas) {
            totalEntradasPagas += (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
        }

        for (PagamentoEntity fatura : entradasAVencer) {
            double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
            totalEntradasAVencer += fatura.getFatura().getValor() - valorPago;
            totalEntradasPagas += valorPago;
        }

        for (PagamentoEntity fatura : entradasVencidas) {
            double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
            totalEntradasVencidas += fatura.getFatura().getValor() - valorPago;
            totalEntradasPagas += valorPago;
        }

        for (PagamentoEntity fatura : entradasEmAbertomesesAnteriores) {
            double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
            totalEntradasEmAbertoMesesAnteriores += fatura.getFatura().getValor() - valorPago;
            totalEntradasPagas += valorPago;
        }

        dashboard.setLanctosEntradaAVencer(entradasAVencer);
        dashboard.setLanctosEntradaVencidos(entradasVencidas);
        dashboard.setTotalEntradasRecebidas(totalEntradasPagas);
        dashboard.setTotalEntradasAVencer(totalEntradasAVencer);
        dashboard.setTotalEntradasVencidas(totalEntradasVencidas);
        dashboard.setTotalEntradasEmAbertoMesesAnteriores(totalEntradasEmAbertoMesesAnteriores);
        dashboard.setTotalEntradasMesAnterior(totalEntradasMesAnterior);        

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            PagamentoRepository saidaRepository = session.get(PagamentoRepository.class);
            saidasPagas = saidaRepository.findPagamentosByFiltro(inicio, fim, "P", "S", List.of("TD"), 0, 0);
            saidasAVencer = saidaRepository.findPagamentosByFiltro(inicio, fim, "A", "S", List.of("TD"), 0, 0);
            saidasVencidas = saidaRepository.findPagamentosByFiltro(inicio, fim, "V", "S", List.of("TD"), 0, 0);
            saidasEmAbertomesesAnteriores = saidaRepository.findPagamentosByFiltro(marcoInicial, inicio.minusDays(1), "V", "S", List.of("TD"), 0, 0);
            totalSaidasMesAnterior = saidaRepository.findTotalPagamentos(inicio.minusMonths(1), fim.minusMonths(1), "TD", "S", List.of("TD"), 0, 0);
        }

        for (PagamentoEntity fatura : saidasPagas) {
            totalSaidasPagas += (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
        }

        for (PagamentoEntity fatura : saidasAVencer) {
            double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
            totalSaidasAVencer += fatura.getFatura().getValor() - valorPago;
            totalSaidasPagas += valorPago;
        }

        for (PagamentoEntity fatura : saidasVencidas) {
            double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
            totalSaidasVencidas += fatura.getFatura().getValor() - valorPago;
            totalSaidasPagas += valorPago;
        }

        for (PagamentoEntity fatura : saidasEmAbertomesesAnteriores) {
            double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
            totalSaidasEmAbertoMesesAnteriores += fatura.getFatura().getValor() - valorPago;
            totalSaidasPagas += valorPago;
        }

        dashboard.setLanctosSaidaAVencer(saidasAVencer);
        dashboard.setLanctosSaidaVencidos(saidasVencidas);
        dashboard.setTotalSaidasPagas(totalSaidasPagas);
        dashboard.setTotalSaidasAVencer(totalSaidasAVencer);
        dashboard.setTotalSaidasVencidas(totalSaidasVencidas);
        dashboard.setTotalSaidasEmAbertoMesesAnteriores(totalSaidasEmAbertoMesesAnteriores);
        dashboard.setTotalSaidasMesAnterior(totalSaidasMesAnterior);

        return dashboard;
    }

    public GraficoDto getGraficoDespesasReceitas() throws Exception {

        GraficoDto grafico = new GraficoDto();
        Object[][] dados = new Object[12][4];
        int indice = 0;

        LocalDate inicio = LocalDate.now().minusMonths(11).withDayOfMonth(1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            PagamentoRepository faturaRepository = session.get(PagamentoRepository.class);

            while (indice < 12) {

                Object[] meses = new Object[4];

                List<PagamentoEntity> saidas = faturaRepository.findPagamentosByFiltro(inicio, fim, "TD", "S", List.of("TD"), 0, 0);
                List<PagamentoEntity> entradas = faturaRepository.findPagamentosByFiltro(inicio, fim, "TD", "E", List.of("TD"), 0, 0);

                double totalSaidas = 0;
                for (PagamentoEntity fatura : saidas) {
                    double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
                    double diferenca = fatura.getFatura().getValor() - valorPago;
                    totalSaidas += (diferenca < 0) ? valorPago : fatura.getFatura().getValor();
                }

                double totalEntradas = 0;
                for (PagamentoEntity fatura : entradas) {
                    double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
                    double diferenca = fatura.getFatura().getValor() - valorPago;
                    totalEntradas += (diferenca < 0) ? valorPago : fatura.getFatura().getValor();
                }

                String mes = (inicio.getMonth().getValue() < 10) ? "0".concat(String.valueOf(inicio.getMonth().getValue())) : String.valueOf(inicio.getMonth().getValue());
                meses[0] = mes.concat("/").concat(String.valueOf(inicio.getYear()));
                meses[1] = totalEntradas;
                meses[2] = totalSaidas;
                meses[3] = totalEntradas - totalSaidas;
                dados[indice] = meses;
                inicio = inicio.plusMonths(1);
                fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
                indice++;
            }

        }

        String[] colunas = {"Meses", "Receita", "Despesa", "Diferença"};
        grafico.setTitulo("Gráfico de despesas e receitas");
        grafico.setTipo("ComboChart");
        grafico.setColunas(colunas);
        grafico.setDados(dados);
        return grafico;
    }

    public GraficoDto getGraficoProjecaoProximos12Meses() throws Exception {

        GraficoDto grafico = new GraficoDto();
        Object[][] dados = new Object[12][4];
        int indice = 0;

        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            PagamentoRepository faturaRepository = session.get(PagamentoRepository.class);

            List<PagamentoEntity> saidasFixas = faturaRepository.findPagamentosByFiltro(inicio, fim, "TD", "S", List.of("F"), 0, 0);
            List<PagamentoEntity> entradasFixas = faturaRepository.findPagamentosByFiltro(inicio, fim, "TD", "E", List.of("F"), 0, 0);

            while (indice < 12) {

                Object[] meses = new Object[4];

                List<PagamentoEntity> saidas = faturaRepository.findPagamentosByFiltro(inicio, fim, "TD", "S", List.of("TD"), 0, 0);
                List<PagamentoEntity> entradas = faturaRepository.findPagamentosByFiltro(inicio, fim, "TD", "E", List.of("TD"), 0, 0);

                if (indice > 0) {
                    saidas.addAll(saidasFixas);
                    entradas.addAll(entradasFixas);
                }

                double totalSaidas = 0;
                for (PagamentoEntity fatura : saidas) {
                    double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
                    double diferenca = fatura.getFatura().getValor() - valorPago;
                    totalSaidas += (diferenca < 0) ? valorPago : fatura.getFatura().getValor();
                }

                double totalEntradas = 0;
                for (PagamentoEntity fatura : entradas) {
                    double valorPago = (fatura.getValor() + fatura.getJuros() + fatura.getMulta()) - fatura.getDesconto();
                    double diferenca = fatura.getFatura().getValor() - valorPago;
                    totalEntradas += (diferenca < 0) ? valorPago : fatura.getFatura().getValor();
                }

                String mes = (inicio.getMonth().getValue() < 10) ? "0".concat(String.valueOf(inicio.getMonth().getValue())) : String.valueOf(inicio.getMonth().getValue());
                meses[0] = mes.concat("/").concat(String.valueOf(inicio.getYear()));
                meses[1] = totalEntradas;
                meses[2] = totalSaidas;
                meses[3] = totalEntradas - totalSaidas;
                dados[indice] = meses;
                inicio = inicio.plusMonths(1);
                fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
                indice++;
            }

        }

        String[] colunas = {"Meses", "Receita", "Despesa", "Diferença"};
        grafico.setTitulo("Gráfico projeção próximos 12 meses");
        grafico.setTipo("ComboChart");
        grafico.setColunas(colunas);
        grafico.setDados(dados);
        return grafico;
    }
    
    public GraficoDto getGraficoDespesasByCategoria() {
        
        GraficoDto grafico = new GraficoDto();
        List<Object[]> list;        
        
        LocalDate dataInicial = LocalDate.now().withDayOfMonth(1);
        LocalDate dataFinal = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
        
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            list = session.get(ProdutoLanctoSaidaRepository.class).findByPeriodo(dataInicial, dataFinal);
        }
        
        Object[][] dados = new Object[list.size()][2];
        int indice = 0;
        
        for(Object[] dado: list) {
            dados[indice] = dado;
            indice++;
        }
        
        grafico.setTitulo("Gráfico despesas por categoria");
        grafico.setTipo("PieChart");
        grafico.setDados(dados);
        
        return grafico;
        
    }

}
