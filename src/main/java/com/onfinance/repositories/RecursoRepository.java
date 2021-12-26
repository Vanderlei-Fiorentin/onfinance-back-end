package com.onfinance.repositories;

import com.onfinance.entities.RecursoEntity;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class RecursoRepository extends Repository<Integer, RecursoEntity> {
    
    public RecursoRepository() {
        super();
    }
    
    public RecursoRepository(Session session) {
        super(session);
    }
    
}