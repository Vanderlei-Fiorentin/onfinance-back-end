package com.onfinance.periodics;

import com.onfinance.entities.PeriodicActionConfiguracaoIntervaloExecucaoEntity;
import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.hibernate.HibernateSessionFactory;
import com.onfinance.repositories.PeriodicActionConfiguracaoIntervaloExecucaoRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 * @param <T>
 */
public abstract class SchedulerAction<T extends TentativaExecucaoPeriodicAction> {

    protected PeriodicActionEntity periodic;

    public SchedulerAction(PeriodicActionEntity periodic) {
        this.periodic = periodic;
    }

    public abstract List<T> getPendingRecords(int maxTentativas, Session session);

    public abstract void processPendingRecords(T record, Session session) throws Exception;

    public void execute() {

        LogUtil.getLogger().log(Level.INFO, "{0}: {1} {2}{3}", new Object[]{LocalDateTime.now(), "Executando", periodic.getNome(), "..."});

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            
            int maxTentativas = periodic.getQuantidadeMaximaExecucao();
            List<T> pendingRecords = getPendingRecords(maxTentativas, session);           
            
            pendingRecords.forEach((record) -> {                
                try {
                    session.getTransaction().begin();
                    processPendingRecords(record, session);
                    session.getTransaction().commit();
                } catch (Exception ex) {
                    session.getTransaction().rollback();
                    LogUtil.getLogger().log(Level.SEVERE, "{0}: \n {2}", new Object[]{LocalDateTime.now(), ex});
                } finally {
                    record.setDataProximaExecucao(getProximaExecucao(record, session));
                    record.incrementarNumeroTentativasRealizadas();
                }
                session.getTransaction().begin();
                session.merge(record);
                session.getTransaction().commit();
            });
            
            LocalDateTime ultimaExecucao = LocalDateTime.now();
            periodic.setUltimaExecucao(ultimaExecucao);
            
            session.getTransaction().begin();
            session.merge(periodic);
            session.getTransaction().commit();
            
        }
    }

    private LocalDateTime getProximaExecucao(T record, Session session) {
        List<PeriodicActionConfiguracaoIntervaloExecucaoEntity> periodics = new PeriodicActionConfiguracaoIntervaloExecucaoRepository(session).findByNome(periodic.getNome());
        return LocalDateTime.now().plusMinutes(periodics.get(record.getNumeroTentativasRealizadas()).getIntervalo());
    }

}
