package com.onfinance.resources;

import com.onfinance.controllers.ProdutoController;
import com.onfinance.entities.ProdutoEntity;
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

@Path(ServicePath.PRODUTOS)
public class ProdutoResource {
    
    private final ProdutoController produtoController;

    public ProdutoResource() {
        this.produtoController = new ProdutoController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<ProdutoEntity> produtos = produtoController.findAll();
            return Response.status(Response.Status.OK).entity(produtos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("{idProduto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idProduto") String idProduto) {
        try {
            ProdutoEntity produto = produtoController.findById(Integer.parseInt(idProduto));
            return Response.status(Response.Status.OK).entity(produto).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(ProdutoEntity produto) {
        try {
            produtoController.save(produto);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(ProdutoEntity produto) {
        try {
            produtoController.update(produto);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idProduto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idProduto") String idProduto) {
        try {
            produtoController.deleteById(Integer.parseInt(idProduto));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}