package com.onfinance.resources;

import com.onfinance.controllers.FaturaController;
import com.onfinance.entities.FaturaEntity;
import com.onfinance.entities.PagamentoEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ServicePath.FATURAS)
public class FaturaResource {

    private final FaturaController faturaController;

    public FaturaResource() {
        this.faturaController = new FaturaController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<FaturaEntity> faturas = faturaController.findAll();
            return Response.status(Response.Status.OK).entity(faturas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idFatura}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idFatura") String idFatura) {
        try {
            FaturaEntity fatura = faturaController.findById(Integer.parseInt(idFatura));
            return Response.status(Response.Status.OK).entity(fatura).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("filtro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findFaturasByFiltro(@QueryParam("empresa") String empresa,
            @QueryParam("periodoInicial") String periodoInicial,
            @QueryParam("periodoFinal") String periodoFinal,
            @QueryParam("situacao") String situacao,
            @QueryParam("tipoLancto") String tipoLancto,
            @QueryParam("tipoPagto") String tipoPagto,
            @QueryParam("usuario") String usuario) {

        LocalDate dataInicial = periodoInicial.equals("null") ? LocalDate.now().withDayOfMonth(1) : LocalDate.parse(periodoInicial);
        LocalDate dataFinal = periodoFinal.equals("null") ? LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()) : LocalDate.parse(periodoFinal);
        List<String> tiposPagamento = Arrays.asList(tipoPagto.split(","));
        
        try {
            List<PagamentoEntity> pagamentos = faturaController.findFaturasByFiltro(Integer.parseInt(empresa), dataInicial, dataFinal, situacao, tipoLancto, tiposPagamento, Integer.parseInt(usuario));
            return Response.status(Response.Status.OK).entity(pagamentos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(FaturaEntity fatura) {
        try {
            faturaController.save(fatura);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(FaturaEntity fatura) {
        try {
            faturaController.update(fatura);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idFatura}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idFatura") String idFatura) {
        try {
            faturaController.deleteById(Integer.parseInt(idFatura));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
