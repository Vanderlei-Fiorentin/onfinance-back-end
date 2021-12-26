package com.onfinance.repositories;

import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PeriodicActionRepository extends Repository<Integer, PeriodicActionEntity> {

    private final XmlQueries queries;
    
    public PeriodicActionRepository() {
        super();
        this.queries = new XmlQueries("PeriodicActionQueries.xml");
    }

    public PeriodicActionRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("PeriodicActionQueries.xml");
    }

    public PeriodicActionEntity findByNome(String nome) {
        return (PeriodicActionEntity) query(queries.get("findByNome"), Map.ofEntries(entry("nome", nome))).list().get(0);
    }

    public List<PeriodicActionEntity> findByStatus(boolean status) {
        return query(queries.get("findByStatus"), Map.ofEntries(entry("status", status))).getResultList();
    }

}
