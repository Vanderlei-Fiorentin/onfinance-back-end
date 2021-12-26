package com.onfinance.resources;

import com.onfinance.controllers.ParcelaLanctoContabilController;
import com.onfinance.entities.ParcelaLanctoContabilEntity;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ServicePath.PARCELAS_LANCTO_CONTABIL)
public class ParcelaLanctoContabilResource {
    
    private final ParcelaLanctoContabilController parcelaLanctoContabilController;
    
    public ParcelaLanctoContabilResource(){
        this.parcelaLanctoContabilController = new ParcelaLanctoContabilController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<ParcelaLanctoContabilEntity> parcelas = parcelaLanctoContabilController.findAll();
            return Response.status(Response.Status.OK).entity(parcelas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("desvinculadas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findParcelasDesvinculadas() {
        try {
            List<ParcelaLanctoContabilEntity> parcelas = parcelaLanctoContabilController.findParcelasDesvinculadas();
            return Response.status(Response.Status.OK).entity(parcelas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("{idParcela}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idParcela") String idParcela) {
        try {
            ParcelaLanctoContabilEntity parcela = parcelaLanctoContabilController.findById(Integer.parseInt(idParcela));
            return Response.status(Response.Status.OK).entity(parcela).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("fatura/{idFatura}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByFatura(@PathParam("idFatura") String idFatura) {
        try {
            List<ParcelaLanctoContabilEntity> parcelas = parcelaLanctoContabilController.findByFatura(Integer.parseInt(idFatura));
            return Response.status(Response.Status.OK).entity(parcelas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("abertas/{idLancto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findParcelasEmAberto(@PathParam("idLancto") String idLancto){  
        try {
            List<ParcelaLanctoContabilEntity> parcelas = parcelaLanctoContabilController.findParcelasEmAberto(Integer.parseInt(idLancto));
            return Response.status(Response.Status.OK).entity(parcelas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("filtro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findParcelasByFiltro(@QueryParam("tipoLancto") String tipoLancto,
            @QueryParam("periodoInicial") String periodoInicial, @QueryParam("periodoFinal") String periodoFinal, 
            @QueryParam("situacao") String situacao){
        
        LocalDate dataInicial = periodoInicial.equals("null") ? LocalDate.now().withDayOfMonth(1) : LocalDate.parse(periodoInicial);
        LocalDate dataFinal = periodoFinal.equals("null") ? LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()) : LocalDate.parse(periodoFinal);
        
        try {
            List<ParcelaLanctoContabilEntity> parcelas = parcelaLanctoContabilController.findParcelasByFiltro(tipoLancto, dataInicial, dataFinal, situacao);
            return Response.status(Response.Status.OK).entity(parcelas).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ParcelaLanctoContabilEntity lancto) {
        try {
            parcelaLanctoContabilController.save(lancto);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @POST
    @Path("/desvincular")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response desvincularParcela(ParcelaLanctoContabilEntity parcela) {
        try {
            parcelaLanctoContabilController.desvincularParcela(parcela);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @POST
    @Path("/vincular")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response vincular(ParcelaLanctoContabilEntity parcela) {
        try {
            parcelaLanctoContabilController.vincularParcela(parcela);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ParcelaLanctoContabilEntity parcela) {
        try {
            parcelaLanctoContabilController.update(parcela);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idParcela}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("idParcela") String idParcela) { 
        try {
            parcelaLanctoContabilController.deleteById(Integer.parseInt(idParcela));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}