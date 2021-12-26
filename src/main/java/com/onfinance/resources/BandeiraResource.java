package com.onfinance.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.controllers.BandeiraController;
import com.onfinance.entities.BandeiraEntity;
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

@Path(ServicePath.BANDEIRAS)
public class BandeiraResource {
    
    private final BandeiraController bandeiraController;

    public BandeiraResource() {
        this.bandeiraController = new BandeiraController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {        
        try {
            List<BandeiraEntity> bandeiras = bandeiraController.findAll();
            return Response.status(Response.Status.OK).entity(bandeiras).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idBandeira}")
    public Response findById(@PathParam("idBandeira") String idBandeira) {
        BandeiraEntity bandeira;
        try {
            bandeira = bandeiraController.findById(Integer.parseInt(idBandeira));
            return Response.status(Response.Status.OK).entity(bandeira).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response save(FormDataMultiPart multiPart, @FormDataParam("bandeira") String bandeiraJson) {
        try {
            Optional<FormDataBodyPart> formData = Optional.ofNullable(multiPart.getField("file"));
            BandeiraEntity bandeira = new ObjectMapper().readValue(bandeiraJson, BandeiraEntity.class);
            bandeiraController.save(formData, bandeira);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(FormDataMultiPart multiPart, @FormDataParam("bandeira") String bandeiraJson) {
        try {
            Optional<FormDataBodyPart> formData = Optional.ofNullable(multiPart.getField("file"));
            BandeiraEntity bandeira = new ObjectMapper().readValue(bandeiraJson, BandeiraEntity.class);
            bandeiraController.update(formData, bandeira);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idBandeira}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("idBandeira") String idBandeira) {
        try {
            bandeiraController.deleteById(Integer.parseInt(idBandeira));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}