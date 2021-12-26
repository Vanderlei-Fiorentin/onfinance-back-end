package com.onfinance.resources;

import com.onfinance.controllers.ProdutoLanctoSaidaController;
import com.onfinance.entities.ProdutoLanctoSaidaEntity;
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

@Path(ServicePath.PRODUTOS_LANCTO_SAIDA)
public class ProdutoLanctoSaidaResource {
    
    private final ProdutoLanctoSaidaController produtoLanctoSaidaController;

    public ProdutoLanctoSaidaResource() {
        this.produtoLanctoSaidaController = new ProdutoLanctoSaidaController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<ProdutoLanctoSaidaEntity> produtos = produtoLanctoSaidaController.findAll();
            return Response.status(Response.Status.OK).entity(produtos).build();
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
            List<ProdutoLanctoSaidaEntity> lanctos = produtoLanctoSaidaController.findByIdLancto(Integer.parseInt(idLancto));
            return Response.status(Response.Status.OK).entity(lanctos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ProdutoLanctoSaidaEntity lancto) {
        try {
            produtoLanctoSaidaController.save(lancto);
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
    public Response saveAll(List<ProdutoLanctoSaidaEntity> lanctos) {
        try {
            produtoLanctoSaidaController.saveAll(lanctos);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ProdutoLanctoSaidaEntity lancto) { 
        try {
            produtoLanctoSaidaController.update(lancto);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(ProdutoLanctoSaidaEntity lancto) { 
        try {
            produtoLanctoSaidaController.delete(lancto);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}