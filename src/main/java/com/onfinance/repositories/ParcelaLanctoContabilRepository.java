package com.onfinance.repositories;

import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.entities.ParcelaLanctoContabilEntity;
import com.onfinance.utils.XmlQueries;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class ParcelaLanctoContabilRepository extends Repository<Integer, ParcelaLanctoContabilEntity> {

    private final XmlQueries queries;

    public ParcelaLanctoContabilRepository() {
        super();
        this.queries = new XmlQueries("ParcelaLanctoContabilQueries.xml");
    }

    public ParcelaLanctoContabilRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("ParcelaLanctoContabilQueries.xml");
    }

    public List<ParcelaLanctoContabilEntity> findParcelasDesvinculadas() {
        return query(queries.get("findParcelasDesvinculadas"), Map.ofEntries()).getResultList();
    }

    public List<ParcelaLanctoContabilEntity> findParcelasAVencerByCompetencia(LocalDate data) {
        return query(queries.get("findParcelasAVencerByCompetencia"), Map.ofEntries(entry("data", data))).getResultList();
    }

    public List<ParcelaLanctoContabilEntity> findParcelasEmAberto(int idLancto) {
        return query(queries.get("findParcelasEmAberto"), Map.ofEntries(entry("idLancto", idLancto))).getResultList();
    }

    public List<ParcelaLanctoContabilEntity> findParcelasByFiltro(LocalDate dataInicial, LocalDate dataFinal, String situacao, String tipoLancto) {
        return query(queries.get("findParcelasByFiltro"), Map.ofEntries(
                entry("dataInicial", dataInicial),
                entry("dataFinal", dataFinal),
                entry("situacao", situacao),
                entry("tipoLancto", tipoLancto),
                entry("today", LocalDate.now()))).getResultList();
    }

    public ParcelaLanctoContabilEntity findByLanctoEntrada(LanctoContabilEntity lancto) {
        return (ParcelaLanctoContabilEntity) query(queries.get("findByLanctoEntrada"), Map.ofEntries(entry("idLancto", lancto.getId()))).list().get(0);
    }

    public List<ParcelaLanctoContabilEntity> findByFatura(int idFatura) {
        return query(queries.get("findByFatura"), Map.ofEntries(entry("idFatura", idFatura))).getResultList();
    }
    
}
