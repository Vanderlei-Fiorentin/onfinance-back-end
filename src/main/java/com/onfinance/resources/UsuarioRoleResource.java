package com.onfinance.resources;

import com.onfinance.controllers.UsuarioRoleController;
import com.onfinance.entities.UsuarioRoleEntity;
import com.onfinance.utils.ServicePath;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path(ServicePath.USUARIOS_ROLES)
public class UsuarioRoleResource {

    private final UsuarioRoleController usuarioRoleController;

    public UsuarioRoleResource() {
        this.usuarioRoleController = new UsuarioRoleController();
    }

    @GET
    public Response findAll() {
        try {
            List<UsuarioRoleEntity> usuarioRoles = usuarioRoleController.findAll();
            return Response.status(Response.Status.OK).entity(usuarioRoles).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @GET
    @Path("{idUsuario}")
    public Response findByUsuario(@PathParam("idUsuario") String idUsuario) {
        try {
            List<UsuarioRoleEntity> usuarioRoles = usuarioRoleController.findByUsuario(Integer.parseInt(idUsuario));
            return Response.status(Response.Status.OK).entity(usuarioRoles).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @POST
    public Response save(UsuarioRoleEntity usuario) {
        try {
            usuarioRoleController.save(usuario);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @PUT
    public Response update(UsuarioRoleEntity usuario) {
        try {
            usuarioRoleController.update(usuario);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @DELETE
    public Response delete(UsuarioRoleEntity usuario) {
        try {
            usuarioRoleController.delete(usuario);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @DELETE
    @Path("{idUsuarioRole}")
    public Response deleteById(@PathParam("idUsuarioRole") String idUsuarioRole) {
        try {
            usuarioRoleController.deleteById(Integer.parseInt(idUsuarioRole));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
