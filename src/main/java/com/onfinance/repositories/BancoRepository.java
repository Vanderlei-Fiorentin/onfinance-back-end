package com.onfinance.repositories;

import com.onfinance.entities.BancoEntity;
import org.hibernate.Session;

public class BancoRepository extends Repository<Integer, BancoEntity> {

    public BancoRepository() {
        super();
    }
    
    public BancoRepository(Session session) {
        super(session);
    }

}