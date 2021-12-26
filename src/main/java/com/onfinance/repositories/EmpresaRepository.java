package com.onfinance.repositories;

import com.onfinance.entities.EmpresaEntity;
import org.hibernate.Session;

public class EmpresaRepository extends Repository<Integer, EmpresaEntity> {
    
    public EmpresaRepository() {
        super();
    }
    
    public EmpresaRepository(Session session) {
        super(session);
    }
}