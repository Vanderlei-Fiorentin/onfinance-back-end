package com.onfinance.repositories;

import com.onfinance.entities.ExtratoBancarioEntity;
import com.onfinance.utils.XmlQueries;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class ExtratoBancarioRepository extends Repository<Integer, ExtratoBancarioEntity> {

    private final XmlQueries queries;

    public ExtratoBancarioRepository() {
        super();
        this.queries = new XmlQueries("ExtratoBancarioQueries.xml");
    }

    public ExtratoBancarioRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("ExtratoBancarioQueries.xml");
    }

    public List<ExtratoBancarioEntity> findByPeriodoAndConta(LocalDate dataInicial, LocalDate dataFinal, String operacao, int conta) {
        return query(queries.get("findByPeriodoAndConta"), Map.ofEntries(
                entry("dataInicial", dataInicial),
                entry("dataFinal", dataFinal),
                entry("operacao", operacao),
                entry("conta", conta))).getResultList();
    }

}
