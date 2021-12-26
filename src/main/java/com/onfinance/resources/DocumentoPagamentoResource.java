package com.onfinance.resources;

import com.onfinance.controllers.DocumentoPagamentoController;
import com.onfinance.entities.DocumentoPagamentoEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path(ServicePath.DOCUMENTOS_FATURA)
public class DocumentoPagamentoResource {

    private final DocumentoPagamentoController documentoPagamentoController;

    public DocumentoPagamentoResource() {
        this.documentoPagamentoController = new DocumentoPagamentoController();
    }

    @GET
    public Response findAll() {
        try {
            List<DocumentoPagamentoEntity> documentos = documentoPagamentoController.findAll();
            return Response.status(Response.Status.OK).entity(documentos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    public Response save(DocumentoPagamentoEntity documento) {
        try {
            documentoPagamentoController.save(documento);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    public Response update(DocumentoPagamentoEntity documento) {
        try {
            documentoPagamentoController.update(documento);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    public Response remove(DocumentoPagamentoEntity documento) {
        try {
            documentoPagamentoController.delete(documento);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
