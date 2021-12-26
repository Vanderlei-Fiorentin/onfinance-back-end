package com.onfinance.repositories;

import com.onfinance.entities.AgenciaEntity;
import org.hibernate.Session;

public class AgenciaRepository extends Repository<Integer, AgenciaEntity>{

    public AgenciaRepository() {
        super();
    }
    
    public AgenciaRepository(Session session) {
        super(session);
    }
    
}