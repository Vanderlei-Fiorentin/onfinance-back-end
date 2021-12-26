package com.onfinance.resources;

import com.onfinance.controllers.TransferenciaBancariaController;
import com.onfinance.entities.TransferenciaBancariaEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path(ServicePath.TRANSFERENCIA_BANCARIA)
public class TransferenciaBancariaResource {
    
    private final TransferenciaBancariaController transferenciaBancariaController;
    
    public TransferenciaBancariaResource() {
        this.transferenciaBancariaController = new TransferenciaBancariaController();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(TransferenciaBancariaEntity transferencia) {
        try {
            transferenciaBancariaController.save(transferencia);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(TransferenciaBancariaEntity transferencia) {  
        try {
            transferenciaBancariaController.update(transferencia);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(TransferenciaBancariaEntity transferencia) {
        try {
            transferenciaBancariaController.delete(transferencia);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}