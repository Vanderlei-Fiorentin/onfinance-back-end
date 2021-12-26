package com.onfinance.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.controllers.UsuarioController;
import com.onfinance.dtos.UsuarioDto;
import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.entities.RoleEntity;
import com.onfinance.entities.UsuarioEntity;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path(ServicePath.USUARIOS)
public class UsuarioResource {

    private final UsuarioController usuarioController;

    public UsuarioResource() {
        this.usuarioController = new UsuarioController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<UsuarioEntity> usuarios = usuarioController.findAll();
            return Response.status(Response.Status.OK).entity(usuarios).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("idUsuario") String idUsuario) {
        try {
            UsuarioDto usuario = usuarioController.findByIdUsuario(Integer.parseInt(idUsuario));
            return Response.status(Response.Status.OK).entity(usuario).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("by-email")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEmail(@QueryParam("email") String email) {
        try {
            UsuarioEntity usuario = usuarioController.findByEmail(email);
            return Response.status(Response.Status.OK).entity(usuario).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(FormDataMultiPart multiPart, @FormDataParam("perfil") String perfilJson,
            @FormDataParam("usuario") String usuarioJson, @FormDataParam("roles") String rolesJson) throws Exception {

        Optional<FormDataBodyPart> foto = Optional.ofNullable(multiPart.getField("foto"));
        PerfilUsuarioEntity perfil = new ObjectMapper().readValue(perfilJson, PerfilUsuarioEntity.class);
        UsuarioEntity usuario = new ObjectMapper().readValue(usuarioJson, UsuarioEntity.class);
        List<RoleEntity> roles = new ObjectMapper().readValue(rolesJson, new TypeReference<List<RoleEntity>>(){});

        try {
            usuarioController.save(usuario, perfil, foto, roles);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(FormDataMultiPart multiPart, @FormDataParam("perfil") String perfilJson, 
            @FormDataParam("usuario") String usuarioJson, @FormDataParam("roles") String rolesJson) throws Exception {

        Optional<FormDataBodyPart> foto = Optional.ofNullable(multiPart.getField("foto"));
        PerfilUsuarioEntity perfil = new ObjectMapper().readValue(perfilJson, PerfilUsuarioEntity.class);
        UsuarioEntity usuario = new ObjectMapper().readValue(usuarioJson, UsuarioEntity.class);
        List<RoleEntity> roles = new ObjectMapper().readValue(rolesJson, new TypeReference<List<RoleEntity>>(){});

        try {
            usuarioController.update(usuario, perfil, foto, roles);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("idUsuario") String idUsuario) {
        try {
            usuarioController.remove(Integer.parseInt(idUsuario));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }

    }

}
