package com.onfinance.repositories;

import com.onfinance.entities.ProdutoEntity;
import org.hibernate.Session;

public class ProdutoRepository extends Repository<Integer, ProdutoEntity> {

    public ProdutoRepository() {
        super();
    }
    
    public ProdutoRepository(Session session) {
        super(session);
    }

}