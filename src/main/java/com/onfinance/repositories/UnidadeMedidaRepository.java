package com.onfinance.repositories;

import com.onfinance.entities.UnidadeMedidaEntity;
import org.hibernate.Session;

public class UnidadeMedidaRepository extends Repository<Integer, UnidadeMedidaEntity> {

    public UnidadeMedidaRepository() {
        super();
    }
    
    public UnidadeMedidaRepository(Session session) {
        super(session);
    }

}