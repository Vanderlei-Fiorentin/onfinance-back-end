package com.onfinance.controllers;

import com.onfinance.entities.ProdutoLanctoSaidaEntity;
import com.onfinance.entities.ProdutoLanctoSaidaID;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.ProdutoLanctoSaidaRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class ProdutoLanctoSaidaController extends Controller<ProdutoLanctoSaidaID, ProdutoLanctoSaidaEntity> {

    public ProdutoLanctoSaidaController() {
        super(ProdutoLanctoSaidaRepository.class);
    }

    public List<ProdutoLanctoSaidaEntity> findByIdLancto(int idLancto) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ProdutoLanctoSaidaRepository.class).findByLanctoSaida(idLancto);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }
    
    public List<Object[]> findByPeriodo(LocalDate dataInicial, LocalDate dataFinal) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(ProdutoLanctoSaidaRepository.class).findByPeriodo(dataInicial, dataFinal);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

}
