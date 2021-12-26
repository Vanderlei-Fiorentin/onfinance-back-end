package com.onfinance.controllers;

import com.onfinance.entities.FaturaEntity;
import com.onfinance.entities.ParcelaLanctoContabilEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.FaturaRepository;
import com.onfinance.repositories.ParcelaLanctoContabilRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

public class ParcelaLanctoContabilController extends Controller<Integer, ParcelaLanctoContabilEntity> {

    public ParcelaLanctoContabilController() {
        super(ParcelaLanctoContabilRepository.class);
    }

    public List<ParcelaLanctoContabilEntity> findByFatura(int idFatura) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ParcelaLanctoContabilRepository.class).findByFatura(idFatura);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public List<ParcelaLanctoContabilEntity> findParcelasEmAberto(int idLancto) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ParcelaLanctoContabilRepository.class).findParcelasEmAberto(idLancto);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public List<ParcelaLanctoContabilEntity> findParcelasByFiltro(String tipoLancto,
            LocalDate dataInicial, LocalDate dataFinal, String situacao) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ParcelaLanctoContabilRepository.class).findParcelasByFiltro(dataInicial, dataFinal, situacao, tipoLancto);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public List<ParcelaLanctoContabilEntity> findParcelasDesvinculadas() throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ParcelaLanctoContabilRepository.class).findParcelasDesvinculadas();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public void desvincularParcela(ParcelaLanctoContabilEntity parcela) {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.openTransaction();
            FaturaEntity fatura = parcela.getFatura();
            parcela.setFatura(null);
            session.get(ParcelaLanctoContabilRepository.class).update(parcela);
            double valor = fatura.getValor() - parcela.getValor();
            fatura.setValor(valor);
            session.get(FaturaRepository.class).update(fatura);
            session.commit();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao desvincular a parcela da fatura!", ex});
        }

    }

    public void vincularParcela(ParcelaLanctoContabilEntity parcela) {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.openTransaction();
            FaturaEntity fatura = parcela.getFatura();
            session.get(ParcelaLanctoContabilRepository.class).update(parcela);
            double valor = fatura.getValor() + parcela.getValor();
            fatura.setValor(valor);
            session.get(FaturaRepository.class).update(fatura);
            session.commit();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao desvincular a parcela da fatura!", ex});
        }

    }

}
