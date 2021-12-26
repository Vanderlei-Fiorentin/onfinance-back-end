package com.onfinance.repositories;

import com.onfinance.entities.FaturaEntity;
import com.onfinance.utils.XmlQueries;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class FaturaRepository extends Repository<Integer, FaturaEntity> {

    private final XmlQueries queries;
    
    public FaturaRepository() {
        super();
        this.queries = new XmlQueries("FaturaQueries.xml");
    }

    public FaturaRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("FaturaQueries.xml");
    }
    
    public List<FaturaEntity> findFaturasVencidasZeradas(LocalDate data, String tipo) {
        return query(queries.get("findFaturasVencidasZeradas"), Map.ofEntries(entry("tipo", tipo), entry("dataAtual", data))).getResultList();
    }

}
