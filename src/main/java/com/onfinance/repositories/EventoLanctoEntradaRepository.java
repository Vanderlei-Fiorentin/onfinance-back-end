package com.onfinance.repositories;

import com.onfinance.entities.EventoLanctoEntradaEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class EventoLanctoEntradaRepository extends Repository<Integer, EventoLanctoEntradaEntity> {

    private final XmlQueries queries;

    public EventoLanctoEntradaRepository() {
        super();
        this.queries = new XmlQueries("EventoLanctoEntradaQueries.xml");
    }

    public EventoLanctoEntradaRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("EventoLanctoEntradaQueries.xml");
    }

    public List<EventoLanctoEntradaEntity> findByLanctoEntrada(int idLancto) {
        return query(queries.get("findByIdLancto"), Map.ofEntries(entry("idLancto", idLancto))).getResultList();
    }

}
