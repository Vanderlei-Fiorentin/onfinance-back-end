package com.onfinance.repositories;

import com.onfinance.entities.PermissaoRecursoEntity;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PermissaoRecursoRepository extends Repository<Integer, PermissaoRecursoEntity>{
    
    public PermissaoRecursoRepository() {
        super();
    }
    
    public PermissaoRecursoRepository(Session session) {
        super(session);
    }
    
}