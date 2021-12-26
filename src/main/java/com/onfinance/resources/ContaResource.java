package com.onfinance.resources;

import com.onfinance.controllers.ContaCorrenteController;
import com.onfinance.entities.ContaEntity;
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

@Path(ServicePath.CONTAS_CORRENTE)
public class ContaResource {

    private final ContaCorrenteController contaCorrenteController;

    public ContaResource() {
        this.contaCorrenteController = new ContaCorrenteController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByStatus(@QueryParam("status") String status) {
        List<ContaEntity> contas;
        try {
            if (Objects.isNull(status)) {
                contas = contaCorrenteController.findAll();
            } else {
                contas = contaCorrenteController.findByStatus(Boolean.valueOf(status));
            }
            return Response.status(Response.Status.OK).entity(contas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idConta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idConta") String idConta) {
        try {
            ContaEntity conta = contaCorrenteController.findById(Integer.parseInt(idConta));
            return Response.status(Response.Status.OK).entity(conta).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ContaEntity conta) {
        try {
            contaCorrenteController.save(conta);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ContaEntity conta) {
        try {
            contaCorrenteController.update(conta);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idConta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idConta") String idConta) {
        try {
            contaCorrenteController.deleteById(Integer.parseInt(idConta));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
