package com.onfinance.periodics;

import com.onfinance.entities.FaturaEntity;
import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.FaturaRepository;
import com.onfinance.repositories.PeriodicActionRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class ExcluirFaturasZeradasPeriodicAction {

    public ExcluirFaturasZeradasPeriodicAction(PeriodicActionEntity periodic) {

    }

    public void execute() throws Exception {

        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Executando ExcluirFaturasZeradasPeriodicAction...."});

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            PeriodicActionEntity periodic = session.get(PeriodicActionRepository.class).findByNome("ExcluirFaturasZeradasPeriodicAction");
            List<FaturaEntity> faturas = session.get(FaturaRepository.class).findFaturasVencidasZeradas(LocalDate.now(), "T");

            faturas.forEach((fatura) -> {
                try {
                    session.openTransaction();
                    session.get(FaturaRepository.class).delete(fatura);
                    session.commit();
                } catch (Exception ex) {
                    LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao remover a fatura do cliente ", fatura.getEmpresa().getNome(), ex});
                }
            });

            // Grava a ultima execução da periodic
            LocalDateTime ultimaExecucao = LocalDateTime.now();

            session.openTransaction();
            periodic.setUltimaExecucao(ultimaExecucao);
            session.get(PeriodicActionRepository.class).update(periodic);
            session.commit();
        }
    }

}
