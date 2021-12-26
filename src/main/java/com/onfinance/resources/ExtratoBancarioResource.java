package com.onfinance.resources;

import com.onfinance.controllers.ExtratoBancarioController;
import com.onfinance.entities.ExtratoBancarioEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ServicePath.EXTRATOS_BANCARIOS)
public class ExtratoBancarioResource {

    private final ExtratoBancarioController extratoBancarioController;

    public ExtratoBancarioResource() {
        this.extratoBancarioController = new ExtratoBancarioController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<ExtratoBancarioEntity> extratos = extratoBancarioController.findAll();
            return Response.status(Response.Status.OK).entity(extratos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("filtro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findExtratosByPeriodoAndConta(@QueryParam("conta") String conta,
            @QueryParam("periodoInicial") String periodoInicial, @QueryParam("periodoFinal") String periodoFinal,
            @QueryParam("operacao") String operacao) {

        LocalDate dataInicial = periodoInicial.equals("null") ? LocalDate.now().withDayOfMonth(1) : LocalDate.parse(periodoInicial);
        LocalDate dataFinal = periodoFinal.equals("null") ? LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()) : LocalDate.parse(periodoFinal);

        try {
            List<ExtratoBancarioEntity> extratos = extratoBancarioController.findByPeriodoAndConta(dataInicial, dataFinal, operacao, Integer.parseInt(conta));
            return Response.status(Response.Status.OK).entity(extratos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ExtratoBancarioEntity extrato) {
        try {
            extratoBancarioController.save(extrato);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ExtratoBancarioEntity extrato) {
        try {
            extratoBancarioController.update(extrato);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(ExtratoBancarioEntity extrato) {
        try {
            extratoBancarioController.delete(extrato);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
