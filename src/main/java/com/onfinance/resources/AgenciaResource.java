package com.onfinance.resources;

import com.onfinance.controllers.AgenciaController;
import com.onfinance.entities.AgenciaEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ServicePath.AGENCIAS)
public class AgenciaResource {
    
    private final AgenciaController agenciaController;

    public AgenciaResource() {
        this.agenciaController = new AgenciaController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<AgenciaEntity> agencias;
        try {
            agencias = agenciaController.findAll();
            return Response.status(Response.Status.OK).entity(agencias).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("{idAgencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idAgencia") String idAgencia) {
        AgenciaEntity agencia;
        try {
            agencia = agenciaController.findById(Integer.parseInt(idAgencia));
            return Response.status(Response.Status.OK).entity(agencia).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(AgenciaEntity agencia) {
        try {
            agenciaController.save(agencia);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(AgenciaEntity agencia) {
        try {
            agenciaController.update(agencia);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idAgencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idAgencia") String idAgencia) {
        try {
            agenciaController.deleteById(Integer.parseInt(idAgencia));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}