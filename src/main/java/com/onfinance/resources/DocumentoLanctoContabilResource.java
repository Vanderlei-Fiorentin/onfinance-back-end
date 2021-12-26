package com.onfinance.resources;

import com.onfinance.controllers.DocumentoLanctoContabilController;
import com.onfinance.entities.DocumentoLanctoContabilEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path(ServicePath.DOCUMENTOS_LANCTO_CONTABIL)
public class DocumentoLanctoContabilResource {
    
    private final DocumentoLanctoContabilController documentoLanctoContabilController;

    public DocumentoLanctoContabilResource() {
        this.documentoLanctoContabilController = new DocumentoLanctoContabilController();        
    }

    @GET
    @Path("{idLancto}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByLancto(@PathParam("idLancto") String idLancto) {
        try {
            List<DocumentoLanctoContabilEntity> documentos = documentoLanctoContabilController.findByLancto(Integer.parseInt(idLancto));            
            return Response.status(Response.Status.OK).entity(documentos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}