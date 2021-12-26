package com.onfinance.repositories;

import com.onfinance.entities.PermissaoEntity;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PermissaoRepository extends Repository<Integer, PermissaoEntity>{
    
    public PermissaoRepository() {
        super();
    }
    
    public PermissaoRepository(Session session) {
        super(session);
    }
    
}