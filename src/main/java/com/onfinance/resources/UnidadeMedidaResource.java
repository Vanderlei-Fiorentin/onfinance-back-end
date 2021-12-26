package com.onfinance.resources;

import com.onfinance.controllers.UnidadeMedidaController;
import com.onfinance.entities.UnidadeMedidaEntity;
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

@Path(ServicePath.UNIDADES_MEDIDAS)
public class UnidadeMedidaResource {

    private final UnidadeMedidaController unidadeMedidaController;

    public UnidadeMedidaResource() {
        this.unidadeMedidaController = new UnidadeMedidaController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<UnidadeMedidaEntity> unidades = unidadeMedidaController.findAll();
            return Response.status(Response.Status.OK).entity(unidades).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idUnidade}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idUnidade") String idUnidade) {
        try {
            UnidadeMedidaEntity unidade = unidadeMedidaController.findById(Integer.parseInt(idUnidade));
            return Response.status(Response.Status.OK).entity(unidade).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(UnidadeMedidaEntity unidade) {
        try {
            unidadeMedidaController.save(unidade);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UnidadeMedidaEntity unidade) {
        try {
            unidadeMedidaController.update(unidade);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idUnidade}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idUnidade") String idUnidade) {
        try {
            unidadeMedidaController.deleteById(Integer.parseInt(idUnidade));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
