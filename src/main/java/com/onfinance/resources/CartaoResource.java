package com.onfinance.resources;

import com.onfinance.controllers.CartaoController;
import com.onfinance.entities.CartaoCreditoEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ServicePath.CARTOES_CREDITO)
public class CartaoResource {
    
    private final CartaoController cartaoController;

    public CartaoResource() {
        this.cartaoController = new CartaoController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByStatus(@QueryParam("status") String status) {
        List<CartaoCreditoEntity> cartoes;
        try {
            if (Objects.isNull(status)) {
                cartoes = cartaoController.findAll();
            } else {
                cartoes = cartaoController.findByStatus(Boolean.valueOf(status));
            }
            return Response.status(Response.Status.OK).entity(cartoes).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: \n {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idCartao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idCartao") String idCartao) {
        try {
            CartaoCreditoEntity cartao = cartaoController.findById(Integer.parseInt(idCartao));
            return Response.status(Response.Status.OK).entity(cartao).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(CartaoCreditoEntity cartao) {
        try {
            cartaoController.save(cartao);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(CartaoCreditoEntity cartao) {
        try {
            cartaoController.update(cartao);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idCartao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idCartao") String idCartao) {
        try {
            cartaoController.deleteById(Integer.parseInt(idCartao));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
