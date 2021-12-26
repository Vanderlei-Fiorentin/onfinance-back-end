package com.onfinance.repositories;

import com.onfinance.entities.BandeiraEntity;
import org.hibernate.Session;


public class BandeiraRepository extends Repository<Integer, BandeiraEntity>{
    
    public BandeiraRepository() {
        super();
    }
    
    public BandeiraRepository(Session session) {
        super(session);
    }
    
}