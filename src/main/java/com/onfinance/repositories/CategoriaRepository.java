package com.onfinance.repositories;

import com.onfinance.entities.CategoriaProdutoEntity;
import org.hibernate.Session;

public class CategoriaRepository extends Repository<Integer, CategoriaProdutoEntity>{

    public CategoriaRepository() {
        super();
    }
    
    public CategoriaRepository(Session session) {
        super(session);
    }
    
}