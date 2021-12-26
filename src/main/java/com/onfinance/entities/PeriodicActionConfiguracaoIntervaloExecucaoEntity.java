package com.onfinance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Entity
@Table(name = "periodic_actions_configuracoes_intervalo_execucao")
public class PeriodicActionConfiguracaoIntervaloExecucaoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracao")
    private int id;

    @JoinColumn(name = "id_periodic")
    @ManyToOne
    private PeriodicActionEntity periodic;

    @Column(name = "intervalo_minutos")
    private int intervalo;

    @Column(name = "ordem")
    private int ordem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PeriodicActionEntity getPeriodic() {
        return periodic;
    }

    public void setPeriodic(PeriodicActionEntity periodic) {
        this.periodic = periodic;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

}
