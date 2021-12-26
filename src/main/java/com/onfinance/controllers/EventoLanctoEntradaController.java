package com.onfinance.controllers;

import com.onfinance.entities.EventoLanctoEntradaEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.EventoLanctoEntradaRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class EventoLanctoEntradaController extends Controller<Integer, EventoLanctoEntradaEntity> {

    public EventoLanctoEntradaController() {
        super(EventoLanctoEntradaRepository.class);
    }

    public List<EventoLanctoEntradaEntity> findByIdLancto(int idLancto) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(EventoLanctoEntradaRepository.class).findByLanctoEntrada(idLancto);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public void deleteByIdLancto(int idLancto) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            List<EventoLanctoEntradaEntity> eventos = session.get(EventoLanctoEntradaRepository.class).findByLanctoEntrada(idLancto);
            session.get(EventoLanctoEntradaRepository.class).deleteAllNewTransaction(eventos);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

}
