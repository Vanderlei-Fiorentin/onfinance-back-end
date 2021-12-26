package com.onfinance.repositories;

import com.onfinance.entities.ContaEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class ContaRepository extends Repository<Integer, ContaEntity> {

    private final XmlQueries queries;
    
    public ContaRepository() {
        super();
        this.queries = new XmlQueries("ContaQueries.xml");
    }
    
    public ContaRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("ContaQueries.xml");
    }
    
    public List<ContaEntity> findByStatus(boolean status) {
        return query(queries.get("findByStatus"), Map.ofEntries(entry("status", status))).getResultList();
    }

}