package com.onfinance.repositories;

import com.onfinance.entities.PeriodicActionConfiguracaoIntervaloExecucaoEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PeriodicActionConfiguracaoIntervaloExecucaoRepository extends Repository<Integer, PeriodicActionConfiguracaoIntervaloExecucaoEntity> {

    private final XmlQueries queries;
    
    public PeriodicActionConfiguracaoIntervaloExecucaoRepository() {
        super();
        this.queries = new XmlQueries("PeriodicActionConfiguracaoIntervaloExecucaoQueries.xml");
    }

    public PeriodicActionConfiguracaoIntervaloExecucaoRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("PeriodicActionConfiguracaoIntervaloExecucaoQueries.xml");
    }

    public List<PeriodicActionConfiguracaoIntervaloExecucaoEntity> findByNome(String nome) {
        return query(queries.get("findByNome"), Map.ofEntries(entry("nome", nome))).getResultList();
    }

}
