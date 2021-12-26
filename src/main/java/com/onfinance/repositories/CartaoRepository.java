package com.onfinance.repositories;

import com.onfinance.entities.CartaoCreditoEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class CartaoRepository extends Repository<Integer, CartaoCreditoEntity> {

    private final XmlQueries queries;
    
    public CartaoRepository() {
        super();
        this.queries = new XmlQueries("CartaoQueries.xml");
    }

    public CartaoRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("CartaoQueries.xml");
    }

    public List<CartaoCreditoEntity> findByStatus(boolean status) {
        return query(queries.get("findByStatus"), Map.ofEntries(entry("status", status))).getResultList();
    }

}
