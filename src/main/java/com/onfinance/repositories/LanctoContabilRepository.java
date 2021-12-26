package com.onfinance.repositories;

import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.utils.XmlQueries;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class LanctoContabilRepository extends Repository<Integer, LanctoContabilEntity> {

    private final XmlQueries queries;

    public LanctoContabilRepository() {
        super();
        this.queries = new XmlQueries("LanctoContabilQueries.xml");
    }

    public LanctoContabilRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("LanctoContabilQueries.xml");
    }

    public LanctoContabilEntity findByParcela(int idParcela) {
        return (LanctoContabilEntity) query(queries.get("findByParcela"), Map.ofEntries(entry("idParcela", idParcela))).list().get(0);
    }

    public LanctoContabilEntity findByFatura(int idFatura) {
        return (LanctoContabilEntity) query(queries.get("findByFatura"), Map.ofEntries(entry("idFatura", idFatura))).list().get(0);
    }

    public List<LanctoContabilEntity> findLanctosByFiltro(String tipo, String situacao, int firstResult, int maxResults) {
        Map<String, Object> parametros = Map.ofEntries(
                entry("tipo", tipo),
                entry("situacao", situacao),
                entry("today", LocalDate.now())
        );
        return query(queries.get("findLanctosByFiltro"), parametros).getResultList();
    }

    public List<LanctoContabilEntity> findLanctosEntradaFixosByDataLancto(LocalDate dataInicial, LocalDate dataFinal, boolean status) {
        Map<String, Object> parametros = Map.ofEntries(
                entry("dataInicial", dataInicial),
                entry("dataFinal", dataFinal),
                entry("status", status)
        );
        return query(queries.get("findLanctosEntradaFixosByDataLancto"), parametros).getResultList();
    }

    public List<LanctoContabilEntity> findLanctosSaidaFixosByDataLancto(LocalDate dataInicial, LocalDate dataFinal, boolean status) {
        Map<String, Object> parametros = Map.ofEntries(
                entry("dataInicial", dataInicial),
                entry("dataFinal", dataFinal),
                entry("status", status)
        );
        return query(queries.get("findLanctosSaidaFixosByDataLancto"), parametros).getResultList();
    }

}
