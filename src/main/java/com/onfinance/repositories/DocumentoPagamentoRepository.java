package com.onfinance.repositories;

import com.onfinance.entities.DocumentoPagamentoEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class DocumentoPagamentoRepository extends Repository<Integer, DocumentoPagamentoEntity> {
    
    private final XmlQueries queries;

    public DocumentoPagamentoRepository() {
        super();
        this.queries = new XmlQueries("DocumentoPagamentoQueries.xml");
    }

    public DocumentoPagamentoRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("DocumentoPagamentoQueries.xml");
    }

    public List<DocumentoPagamentoEntity> findByUpload(boolean status) {
        return query(queries.get("findByUpload"), Map.ofEntries(entry("status", status))).getResultList();
    }

    public List<DocumentoPagamentoEntity> findByUploadAndNumeroTentativas(boolean status, int maxTentativas) {
        return query(queries.get("findByUploadAndNumeroTentativas"), Map.ofEntries(entry("status", status), entry("maxTentativas", maxTentativas))).getResultList();
    }

}
