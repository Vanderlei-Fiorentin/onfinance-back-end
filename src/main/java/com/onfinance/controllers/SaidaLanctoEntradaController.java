package com.onfinance.controllers;

import com.onfinance.entities.SaidaLanctoEntradaEntity;
import com.onfinance.entities.SaidaLanctoEntradaID;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.SaidaLanctoEntradaRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class SaidaLanctoEntradaController extends Controller<SaidaLanctoEntradaID, SaidaLanctoEntradaEntity> {

    public SaidaLanctoEntradaController() {
        super(SaidaLanctoEntradaRepository.class);
    }

    public List<SaidaLanctoEntradaEntity> findByIdLancto(int idLancto) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(SaidaLanctoEntradaRepository.class).findByIdLancto(idLancto);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

}
