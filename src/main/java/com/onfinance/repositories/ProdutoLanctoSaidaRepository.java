package com.onfinance.repositories;

import com.onfinance.entities.ProdutoLanctoSaidaEntity;
import com.onfinance.utils.XmlQueries;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class ProdutoLanctoSaidaRepository extends Repository<Integer, ProdutoLanctoSaidaEntity> {

    private final XmlQueries queries;
    
    public ProdutoLanctoSaidaRepository() {
        super();
        this.queries = new XmlQueries("ProdutoLanctoSaidaQueries.xml");
    }

    public ProdutoLanctoSaidaRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("ProdutoLanctoSaidaQueries.xml");
    }

    public List<ProdutoLanctoSaidaEntity> findByLanctoSaida(int idLancto) {
        return query(queries.get("findByLanctoSaida"), Map.ofEntries(entry("idLancto", idLancto))).getResultList();
    }
    
    public List<Object[]> findByPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        return query(queries.get("findByPeriodo"), Map.ofEntries(entry("dataInicial", dataInicial), entry("dataFinal", dataFinal))).getResultList();
    }

}
