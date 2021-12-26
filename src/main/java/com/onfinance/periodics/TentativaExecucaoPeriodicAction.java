package com.onfinance.periodics;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Vanderlei Fiorentin
 */
public abstract class TentativaExecucaoPeriodicAction implements Serializable {    

    public abstract int getNumeroTentativasRealizadas();

    public abstract void setNumeroTentativasRealizadas(int numeroTentativasRealizadas);

    public abstract LocalDateTime getDataProximaExecucao();

    public abstract void setDataProximaExecucao(LocalDateTime dataProximaExecucao);

    public abstract void incrementarNumeroTentativasRealizadas();
}
