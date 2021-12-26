package com.onfinance.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.controllers.BancoController;
import com.onfinance.entities.BancoEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.io.InputStream;
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

@Path(ServicePath.BANCOS)
public class BancoResource {

    private final BancoController bancoController;

    public BancoResource() {
        bancoController = new BancoController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<BancoEntity> bancos;
        try {
            bancos = bancoController.findAll();
            return Response.status(Response.Status.OK).entity(bancos).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idBanco}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idBanco") String idBanco) {
        BancoEntity banco;
        try {
            banco = bancoController.findById(Integer.parseInt(idBanco));
            return Response.status(Response.Status.OK).entity(banco).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response save(FormDataMultiPart multiPart, @FormDataParam("banco") String jsonBanco) {
        try { 
            Optional<FormDataBodyPart> formData = Optional.ofNullable(multiPart.getField("file"));
            BancoEntity banco = new ObjectMapper().readValue(jsonBanco, BancoEntity.class);  
            bancoController.save(formData, banco);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(FormDataMultiPart multiPart, @FormDataParam("banco") String jsonBanco) {
        try {
            Optional<FormDataBodyPart> formData = Optional.ofNullable(multiPart.getField("file"));
            BancoEntity banco = new ObjectMapper().readValue(jsonBanco, BancoEntity.class); 
            bancoController.update(formData, banco);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idBanco}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("idBanco") String idBanco) {
        try {
            BancoEntity banco = bancoController.findById(Integer.parseInt(idBanco));
            bancoController.remove(banco);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}