package com.onfinance.repositories;

import com.onfinance.entities.EventoEntity;
import org.hibernate.Session;

public class EventoRepository extends Repository<Integer, EventoEntity> {

    public EventoRepository() {
        super();
    }
    
    public EventoRepository(Session session) {
        super(session);
    }
    
}