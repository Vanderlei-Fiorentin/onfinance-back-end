package com.onfinance.repositories;

import com.onfinance.entities.DocumentoLanctoContabilEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

public class DocumentoLanctoContabilRepository extends Repository<Integer, DocumentoLanctoContabilEntity> {

    private final XmlQueries queries;
    
    public DocumentoLanctoContabilRepository() {
        super();
        this.queries = new XmlQueries("DocumentoLanctoContabilQueries.xml");
    }

    public DocumentoLanctoContabilRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("DocumentoLanctoContabilQueries.xml");
    }

    public List<DocumentoLanctoContabilEntity> findByLancto(int idLancto) {
        return query(queries.get("findByLancto"), Map.ofEntries(entry("idLancto", idLancto))).getResultList();
    }

    public List<DocumentoLanctoContabilEntity> findByUpload(boolean status) {
        return query(queries.get("findByUpload"), Map.ofEntries(entry("status", status))).getResultList();
    }

    public List<DocumentoLanctoContabilEntity> findByUploadAndNumeroTentativas(boolean status, int maxTentativas) {
        return query(queries.get("findByUploadAndNumeroTentativas"), Map.ofEntries(entry("status", status), entry("maxTentativas", maxTentativas))).getResultList();
    }
}
