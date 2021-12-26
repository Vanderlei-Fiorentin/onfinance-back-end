package com.onfinance.controllers;

import com.onfinance.entities.ExtratoBancarioEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.ExtratoBancarioRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

public class ExtratoBancarioController extends Controller<Integer, ExtratoBancarioEntity> {

    public ExtratoBancarioController() {
        super(ExtratoBancarioRepository.class);
    }

    public List<ExtratoBancarioEntity> findByPeriodoAndConta(LocalDate dataInicial, LocalDate dataFinal, String operacao, int conta) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ExtratoBancarioRepository.class).findByPeriodoAndConta(dataInicial, dataFinal, operacao, conta);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

}
