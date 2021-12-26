package com.onfinance.periodics;

import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.repositories.PeriodicActionRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class TestePeriodicAction {

    public TestePeriodicAction() {
        
    }

    public void execute() {

        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Executando TestePeriodicAction...."});
        
        PeriodicActionRepository repository = new PeriodicActionRepository();
        PeriodicActionEntity periodic = repository.findByNome("TestePeriodicAction");

        // Grava a ultima execução da periodic
        LocalDateTime ultimaExecucao = LocalDateTime.now();
        periodic.setUltimaExecucao(ultimaExecucao);
        repository.update(periodic);
    }
    
}