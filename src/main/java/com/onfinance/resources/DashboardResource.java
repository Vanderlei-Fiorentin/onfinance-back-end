package com.onfinance.resources;

import com.onfinance.controllers.DashboardController;
import com.onfinance.dtos.DashboardDto;
import com.onfinance.dtos.GraficoDto;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ServicePath.DASHBOARD)
public class DashboardResource {

    private final DashboardController dashboardController;

    public DashboardResource() {
        dashboardController = new DashboardController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDespesasReceitas() {

        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fim = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        DashboardDto dashboard;

        try {
            dashboard = dashboardController.getReceitasDespesasByCompetencia(inicio, fim);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }

        return Response.status(Response.Status.OK).entity(dashboard).build();
    }

    @GET
    @Path("grafico-despesas-receitas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraficoDespesasReceitas() {
        try {
            GraficoDto grafico = dashboardController.getGraficoDespesasReceitas();
            return Response.status(Response.Status.OK).entity(grafico).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("grafico-projecao")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraficoProjecaoProximos12Meses() {
        try {
            GraficoDto grafico = dashboardController.getGraficoProjecaoProximos12Meses();
            return Response.status(Response.Status.OK).entity(grafico).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("grafico-detalhe-despesas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraficoDespesasByCategoria() {
        try {
            GraficoDto grafico = dashboardController.getGraficoDespesasByCategoria();
            return Response.status(Response.Status.OK).entity(grafico).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}