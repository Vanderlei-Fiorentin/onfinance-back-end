package com.onfinance.repositories;

import com.onfinance.entities.PagamentoEntity;
import com.onfinance.utils.XmlQueries;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PagamentoRepository extends Repository<Integer, PagamentoEntity> {

    private final XmlQueries queries;

    public PagamentoRepository() {
        super();
        this.queries = new XmlQueries("PagamentoQueries.xml");
    }

    public PagamentoRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("PagamentoQueries.xml");
    }

    public List<PagamentoEntity> findPagamentosByFiltro(LocalDate dataInicial, LocalDate dataFinal, String situacao, String tipoLancto, List<String> tiposPagto, int empresa, int usuario) {

        Map<String, Object> parameters = Map.ofEntries(entry("dataInicial", dataInicial),
                entry("dataFinal", dataFinal),
                entry("tipoLancto", tipoLancto),
                entry("tipoPagto", tiposPagto),
                entry("empresa", empresa),
                entry("usuario", usuario),
                entry("situacao", situacao),
                entry("today", LocalDate.now()));

        List<PagamentoEntity> pagamentos = query(queries.get("findPagamentosByFiltro"), parameters).getResultList();

        Collections.sort(pagamentos, (PagamentoEntity p1, PagamentoEntity p2) -> {
            if (p1.getFatura().getVencimento() == null || p2.getFatura().getVencimento() == null) {
                return 0;
            }
            return p1.getFatura().getVencimento().compareTo(p2.getFatura().getVencimento());
        });

        return pagamentos;
    }
    
    public double findTotalPagamentos(LocalDate dataInicial, LocalDate dataFinal, String situacao, String tipoLancto, List<String> tiposPagto, int empresa, int usuario) {

        Map<String, Object> parameters = Map.ofEntries(entry("dataInicial", dataInicial),
                entry("dataFinal", dataFinal),
                entry("tipoLancto", tipoLancto),
                entry("tipoPagto", tiposPagto),
                entry("empresa", empresa),
                entry("usuario", usuario),
                entry("situacao", situacao),
                entry("today", LocalDate.now()));

        List pagamentos = query(queries.get("findTotalPagamentos"), parameters).list();

        return (pagamentos.get(0) == null) ? 0 : ((double) pagamentos.get(0));
    }

    public PagamentoEntity findByFatura(int idFatura) {
        return (PagamentoEntity) query(queries.get("findByFatura"), Map.ofEntries(entry("idFatura", idFatura))).getSingleResult();
    }

}
