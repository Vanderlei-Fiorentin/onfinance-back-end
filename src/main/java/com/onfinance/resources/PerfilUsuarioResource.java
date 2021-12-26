package com.onfinance.resources;

import com.onfinance.controllers.PerfilUsuarioController;
import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path(ServicePath.PERFIL_USUARIO)
public class PerfilUsuarioResource {
    
    private final PerfilUsuarioController perfilUsuarioController;

    public PerfilUsuarioResource() {
        this.perfilUsuarioController = new PerfilUsuarioController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            List<PerfilUsuarioEntity> perfis = perfilUsuarioController.findAll();
            return Response.status(Response.Status.OK).entity(perfis).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByIdUsuario(@PathParam("idUsuario") String idUsuario) {
        try {
            PerfilUsuarioEntity perfil = perfilUsuarioController.findByIdUsuario(Integer.parseInt(idUsuario))
                    .orElseThrow(() -> new Exception("Usuário não localizado na base de dados!"));
            return Response.status(Response.Status.OK).entity(perfil).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
}