package com.onfinance.resources;

import com.onfinance.controllers.CategoriaController;
import com.onfinance.entities.CategoriaProdutoEntity;
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

@Path(ServicePath.CATEGORIAS)
public class CategoriaResource {

    private final CategoriaController categoriaController;

    public CategoriaResource() {
        this.categoriaController = new CategoriaController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<CategoriaProdutoEntity> categorias = categoriaController.findAll();
            return Response.status(Response.Status.OK).entity(categorias).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idCategoria") String idCategoria) {
        try {
            CategoriaProdutoEntity categoria = categoriaController.findById(Integer.parseInt(idCategoria));
            return Response.status(Response.Status.OK).entity(categoria).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(CategoriaProdutoEntity categoria) {
        try {
            categoriaController.save(categoria);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(CategoriaProdutoEntity categoria) {
        try {
            categoriaController.update(categoria);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idCategoria") String idCategoria) {
        try {
            categoriaController.deleteById(Integer.parseInt(idCategoria));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
