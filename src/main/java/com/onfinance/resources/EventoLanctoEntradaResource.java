package com.onfinance.resources;

import com.onfinance.controllers.EventoLanctoEntradaController;
import com.onfinance.entities.EventoLanctoEntradaEntity;
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

@Path(ServicePath.EVENTOS_LANCTO_ENTRADA)
public class EventoLanctoEntradaResource {
    
    private final EventoLanctoEntradaController eventoLanctoEntradaController;

    public EventoLanctoEntradaResource() {
        this.eventoLanctoEntradaController = new EventoLanctoEntradaController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<EventoLanctoEntradaEntity> eventos = eventoLanctoEntradaController.findAll();
            return Response.status(Response.Status.OK).entity(eventos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }  
    
    @GET
    @Path("{idLancto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByIdLancto(@PathParam("idLancto") String idLancto) {
        try {
            List<EventoLanctoEntradaEntity> lanctos = eventoLanctoEntradaController.findByIdLancto(Integer.parseInt(idLancto));
            return Response.status(Response.Status.OK).entity(lanctos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(EventoLanctoEntradaEntity evento) {
        try {
            eventoLanctoEntradaController.save(evento);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAll(List<EventoLanctoEntradaEntity> eventos) {
        try {
            eventoLanctoEntradaController.saveAll(eventos);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(EventoLanctoEntradaEntity evento) { 
        try {
            eventoLanctoEntradaController.update(evento);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idLancto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteByIdLancto(@PathParam("idLancto") String idLancto) { 
        try {
            eventoLanctoEntradaController.deleteByIdLancto(Integer.parseInt(idLancto));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}