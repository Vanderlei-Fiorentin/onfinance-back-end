package com.onfinance.periodics;

import com.onfinance.controllers.LanctoContabilController;
import com.onfinance.entities.EventoLanctoEntradaEntity;
import com.onfinance.dtos.LanctoContabilGeralDto;
import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.entities.ParcelaLanctoContabilEntity;
import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.entities.ProdutoLanctoSaidaEntity;
import com.onfinance.entities.SaidaLanctoEntradaEntity;
import com.onfinance.entities.SaidaLanctoEntradaID;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.EventoLanctoEntradaRepository;
import com.onfinance.repositories.LanctoContabilRepository;
import com.onfinance.repositories.ParcelaLanctoContabilRepository;
import com.onfinance.repositories.PeriodicActionRepository;
import com.onfinance.repositories.ProdutoLanctoSaidaRepository;
import com.onfinance.repositories.SaidaLanctoEntradaRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class LanctoFixoPeriodicAction {

    public LanctoFixoPeriodicAction(PeriodicActionEntity periodic) {

    }

    public void execute() throws Exception {

        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Executando LanctoFixoPeriodicAction...."});

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            PeriodicActionEntity periodic = session.get(PeriodicActionRepository.class).findByNome("LanctoFixoPeriodicAction");

            gerarLanctosEntradaFixos(session);
            gerarLanctosSaidaFixos(session);

            // Grava a ultima execução da periodic        
            LocalDateTime ultimaExecucao = LocalDateTime.now();
            periodic.setUltimaExecucao(ultimaExecucao);

            session.openTransaction();
            session.get(PeriodicActionRepository.class).update(periodic);
            session.commit();
        }
    }

    public void gerarLanctosSaidaFixos(HibernateSession session) {
        LocalDate dataInicial = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate dataFinal = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
        // Obtem os lançamentos fixos
        List<LanctoContabilEntity> lanctos = session.get(LanctoContabilRepository.class)
                .findLanctosSaidaFixosByDataLancto(dataInicial, dataFinal, true);
        lanctos.forEach((lancto) -> {
            try {
                session.openTransaction();
                saveLanctosSaidaFixos(lancto, session);
                session.commit();
            } catch (Exception ex) {
                session.rollback();
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao gerar os lançamentos de saída!", ex});
            }
        });
    }

    public void gerarLanctosEntradaFixos(HibernateSession session) {
        LocalDate dataInicial = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        LocalDate dataFinal = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
        // Obtem as parcelas os lançamentos fixos
        List<LanctoContabilEntity> lanctos = session.get(LanctoContabilRepository.class)
                .findLanctosEntradaFixosByDataLancto(dataInicial, dataFinal, true);
        lanctos.forEach((lancto) -> {
            try {
                session.openTransaction();
                saveLanctosEntradaFixos(lancto, session);
                session.commit();
            } catch (Exception ex) {
                session.rollback();
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao gerar os lançamentos de entrada!", ex});
            }
        });
    }

    public void saveLanctosEntradaFixos(LanctoContabilEntity lancto, HibernateSession session) throws Exception {
        LanctoContabilGeralDto lanctoContabilGeral = new LanctoContabilGeralDto();
        LanctoContabilEntity lanctoContabilEntrada = (LanctoContabilEntity) lancto.clone();
        // Obtem os eventos vinculados ao lançamento contábil
        List<EventoLanctoEntradaEntity> eventos = session.get(EventoLanctoEntradaRepository.class).findByLanctoEntrada(lancto.getId());
        List<SaidaLanctoEntradaEntity> saidas = session.get(SaidaLanctoEntradaRepository.class).findByIdLancto(lancto.getId());
        // Atualiza os dados do lançamento
        LocalDate novoVencimento = lancto.getDataLancto().plusMonths(1);
        lanctoContabilEntrada.setId(0);
        lanctoContabilEntrada.setDataLancto(novoVencimento);
        // Cria o lançamento geral
        lanctoContabilGeral.setLancto(lanctoContabilEntrada);
        lanctoContabilGeral.setEventos(eventos);
        // Busca a próxima parcela em aberto do lançamento de saída 
        List<SaidaLanctoEntradaEntity> listaSaidas = gerarSaidasLanctoEntrada(saidas, lanctoContabilEntrada, session);
        lanctoContabilGeral.setSaidas(listaSaidas);
        // Salva o lançamento 
        new LanctoContabilController().save(lanctoContabilGeral, Optional.ofNullable(null));
        lancto.setAtivo(false);
        session.get(LanctoContabilRepository.class).update(lancto);
        session.flush();
    }

    public void saveLanctosSaidaFixos(LanctoContabilEntity lancto, HibernateSession session) throws Exception {
        LanctoContabilGeralDto lanctoContabilGeral = new LanctoContabilGeralDto();
        LanctoContabilEntity lanctoContabilSaida = (LanctoContabilEntity) lancto.clone();
        // Obtem os produtos vinculados ao lançamento contábil
        List<ProdutoLanctoSaidaEntity> produtos = session.get(ProdutoLanctoSaidaRepository.class).findByLanctoSaida(lancto.getId());
        // Atualiza os dados do lançamento
        LocalDate novoVencimento = lancto.getDataLancto().plusMonths(1);
        lanctoContabilSaida.setId(0);
        lanctoContabilSaida.setDataLancto(novoVencimento);
        // Cria o lançamento geral
        lanctoContabilGeral.setLancto(lanctoContabilSaida);
        lanctoContabilGeral.setProdutos(produtos);
        // Salva o lançamento
        new LanctoContabilController().save(lanctoContabilGeral, Optional.ofNullable(null));
        lancto.setAtivo(false);
        session.get(LanctoContabilRepository.class).update(lancto);
        session.flush();
    }

    public List<SaidaLanctoEntradaEntity> gerarSaidasLanctoEntrada(List<SaidaLanctoEntradaEntity> saidas, LanctoContabilEntity lanctoContabilEntrada, HibernateSession session) {
        List<SaidaLanctoEntradaEntity> listaSaidas = new ArrayList<>();
        saidas.forEach(saida -> {
            int idlancto = saida.getSaidaLanctoEntradaID().getParcelaLanctoSaida().getLanctoContabil().getId();
            List<ParcelaLanctoContabilEntity> parcelas = session.get(ParcelaLanctoContabilRepository.class).findParcelasEmAberto(idlancto);
            if (parcelas.isEmpty()) {
                return;
            }
            ParcelaLanctoContabilEntity parcela = parcelas.stream().filter(
                    p -> p.getParcela() > saida.getSaidaLanctoEntradaID().getParcelaLanctoSaida().getParcela()
            ).collect(Collectors.toList()).get(0);
            SaidaLanctoEntradaEntity saidaLanctoEntrada = new SaidaLanctoEntradaEntity();
            SaidaLanctoEntradaID saidaLanctoEntradaID = new SaidaLanctoEntradaID();
            saidaLanctoEntradaID.setLanctoEntrada(lanctoContabilEntrada);
            saidaLanctoEntradaID.setParcelaLanctoSaida(parcela);
            saidaLanctoEntrada.setSaidaLanctoEntradaID(saidaLanctoEntradaID);
            listaSaidas.add(saidaLanctoEntrada);
        });
        return listaSaidas;
    }
}
