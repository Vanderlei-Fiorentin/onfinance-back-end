package com.onfinance.repositories;

import com.onfinance.entities.SaidaLanctoEntradaEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class SaidaLanctoEntradaRepository extends Repository<Integer, SaidaLanctoEntradaEntity> {

    private final XmlQueries queries;
    
    public SaidaLanctoEntradaRepository() {
        super();
        this.queries = new XmlQueries("SaidaLanctoEntradaQueries.xml");
    }

    public SaidaLanctoEntradaRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("SaidaLanctoEntradaQueries.xml");
    }

    public List<SaidaLanctoEntradaEntity> findByIdLancto(int idLancto) {
        return query(queries.get("findByIdLancto"), Map.ofEntries(entry("idLancto", idLancto))).getResultList();
    }

}
