package com.onfinance.periodics;

import com.onfinance.entities.DocumentoLanctoContabilEntity;
import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.repositories.DocumentoLanctoContabilRepository;
import com.onfinance.utils.FileUtil;
import com.onfinance.utils.GerenciadorArquivosUtil;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import org.hibernate.Session;
import java.net.http.HttpResponse;
import java.util.Objects;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UploadDocumentosLanctoPeriodicAction extends SchedulerAction<DocumentoLanctoContabilEntity> {

    private final String UPLOAD_PATH;
    
    public UploadDocumentosLanctoPeriodicAction(PeriodicActionEntity periodic) {
        super(periodic);
        UPLOAD_PATH = PropertyUtil.get("com.onfinance.files");
    }

    @Override
    public List<DocumentoLanctoContabilEntity> getPendingRecords(int maxTentativas, Session session) {
        return new DocumentoLanctoContabilRepository(session).findByUploadAndNumeroTentativas(false, maxTentativas);
    }

    @Override
    public void processPendingRecords(DocumentoLanctoContabilEntity documento, Session session) throws Exception {
        
        String idDocumento = null;
        HttpResponse response;
        
        GerenciadorArquivosUtil gerenciador = new GerenciadorArquivosUtil();
        File file = new File(UPLOAD_PATH + documento.getNome());        
        
        String token = (String) gerenciador.login().body();
        response = gerenciador.send(file, documento.getNome(), token, 5);        
                
        try {
            if(response.statusCode() >= 200 && response.statusCode() < 300) {
                idDocumento = (String) response.body(); 
            } 
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao realizar upload dos documentos de lanctos para o Google Drive!", ex});
            throw new Exception(ex);
        }
        
        if (Objects.isNull(idDocumento)) {
            throw new Exception("ID do documento não pode ser vazio/nulo!");
        }
        
        documento.setUpload(true);
        documento.setDocumento(idDocumento);
        session.merge(documento);
        
        new FileUtil().remove(UPLOAD_PATH, documento.getNome());
    }

}
