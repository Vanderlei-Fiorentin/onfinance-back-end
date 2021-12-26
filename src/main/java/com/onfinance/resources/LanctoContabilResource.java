package com.onfinance.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.controllers.LanctoContabilController;
import com.onfinance.dtos.LanctoContabilGeralDto;
import com.onfinance.entities.LanctoContabilEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path(ServicePath.LANCTOS_CONTABEIS)
public class LanctoContabilResource {

    private final LanctoContabilController lanctoContabilController;

    public LanctoContabilResource() {
        this.lanctoContabilController = new LanctoContabilController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByFiltro(@QueryParam("tipo") String tipo, @QueryParam("status") String status,
            @QueryParam("page") String page, @QueryParam("page_size") String pageSize) {
        
        try {      
            int numberPage = Objects.isNull(page) ? 1 : Integer.parseInt(page);
            int maxResult = numberPage * (Objects.isNull(pageSize) ? 11 : Integer.parseInt(pageSize));
            int firstResult = maxResult - numberPage;
            List<LanctoContabilEntity> lanctos = lanctoContabilController.findLanctosByFiltro(tipo, status, firstResult, maxResult);
            return Response.status(Response.Status.OK).entity(lanctos).build();
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
            LanctoContabilEntity lancto = lanctoContabilController.findById(Integer.parseInt(idLancto));
            return Response.status(Response.Status.OK).entity(lancto).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("parcelas/{idParcela}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByParcela(@PathParam("idParcela") String idParcela) {
        try {
            LanctoContabilEntity lancto = lanctoContabilController.findByParcela(Integer.parseInt(idParcela));
            return Response.status(Response.Status.OK).entity(lancto).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("faturas/{idFatura}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByFatura(@PathParam("idFatura") String idFatura) {
        try {
            LanctoContabilEntity lancto = lanctoContabilController.findByFatura(Integer.parseInt(idFatura));
            return Response.status(Response.Status.OK).entity(lancto).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response save(FormDataMultiPart multiPart, @FormDataParam("lancto") String lanctoJson) {
        try {
            Optional<List<FormDataBodyPart>> formsData = Optional.ofNullable(multiPart.getFields("files"));
            LanctoContabilGeralDto lanctoGeral = new ObjectMapper().readValue(lanctoJson, LanctoContabilGeralDto.class);
            lanctoContabilController.save(lanctoGeral, formsData);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(FormDataMultiPart multiPart, @FormDataParam("lancto") String lanctoJson) {
        try {
            Optional<List<FormDataBodyPart>> formsData = Optional.ofNullable(multiPart.getFields("files"));
            LanctoContabilGeralDto lanctoGeral = new ObjectMapper().readValue(lanctoJson, LanctoContabilGeralDto.class);
            lanctoContabilController.update(lanctoGeral, formsData);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(LanctoContabilEntity lancto) {
        try {
            lanctoContabilController.delete(lancto);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
