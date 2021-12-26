package com.onfinance.controllers;

import com.onfinance.entities.DocumentoLanctoContabilEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.DocumentoLanctoContabilRepository;
import com.onfinance.utils.LogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class DocumentoLanctoContabilController extends Controller<Integer, DocumentoLanctoContabilEntity> {

    public DocumentoLanctoContabilController() {
        super(DocumentoLanctoContabilRepository.class);
    }

    public List<DocumentoLanctoContabilEntity> findByLancto(int idLancto) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(DocumentoLanctoContabilRepository.class).findByLancto(idLancto);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

}
