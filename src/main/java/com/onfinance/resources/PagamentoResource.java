package com.onfinance.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.controllers.PagamentoController;
import com.onfinance.entities.PagamentoEntity;
import com.onfinance.dtos.ParcelamentoDto;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path(ServicePath.PAGAMENTOS)
public class PagamentoResource {

    private final PagamentoController pagamentoController;

    public PagamentoResource() {
        this.pagamentoController = new PagamentoController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<PagamentoEntity> pagamentos = pagamentoController.findAll();
            return Response.status(Response.Status.OK).entity(pagamentos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idFatura}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findByFatura(@PathParam("idFatura") String idFatura) {
        try {
            PagamentoEntity pagamento = pagamentoController.findByFatura(Integer.parseInt(idFatura));
            return Response.status(Response.Status.OK).entity(pagamento).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response save(FormDataMultiPart multiPart, @FormDataParam("pagamento") String pagamentoJson, @FormDataParam("parcelamento") String parcelamentoJson) {
        try {
            Optional<List<FormDataBodyPart>> formsData = Optional.ofNullable(multiPart.getFields("files"));
            PagamentoEntity pagamento = new ObjectMapper().readValue(pagamentoJson, PagamentoEntity.class);
            ParcelamentoDto parcelamento = new ObjectMapper().readValue(parcelamentoJson, ParcelamentoDto.class);
            new PagamentoController().save(pagamento, parcelamento, formsData);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(PagamentoEntity pagamento) {
        try {
            pagamentoController.update(pagamento);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idPagamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idPagamento") String idPagamento) {
        try {
            pagamentoController.deleteById(Integer.parseInt(idPagamento));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
