package com.onfinance.resources;

import com.onfinance.controllers.PermissaoRoleController;
import com.onfinance.entities.PermissaoRoleEntity;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
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
@Path(ServicePath.PERMISSOES_ROLES)
public class PermissaoRoleResource {

    private final PermissaoRoleController permissaoRoleController;

    public PermissaoRoleResource() {
        this.permissaoRoleController = new PermissaoRoleController();
    }

    @GET
    public Response findAll() {
        try {
            List<PermissaoRoleEntity> roles = permissaoRoleController.findAll();
            return Response.status(Response.Status.OK).entity(roles).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("permissao/{idPermissao}")
    public Response findByPermissao(@PathParam("idPermissao") String idPermissao) {
        try {
            List<PermissaoRoleEntity> roles = permissaoRoleController.findByPermissao(Integer.parseInt(idPermissao));
            return Response.status(Response.Status.OK).entity(roles).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("role/{idRole}")
    public Response findByRole(@PathParam("idRole") String idRole) {
        try {
            List<PermissaoRoleEntity> roles = permissaoRoleController.findByRole(Integer.parseInt(idRole));
            return Response.status(Response.Status.OK).entity(roles).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    public Response save(PermissaoRoleEntity role) {
        try {
            permissaoRoleController.save(role);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @POST
    @Path("list")
    public Response saveAll(List<PermissaoRoleEntity> roles) {
        try {
            permissaoRoleController.saveAll(roles);
            return Response.status(Response.Status.CREATED).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    public Response update(PermissaoRoleEntity role) {
        try {
            permissaoRoleController.update(role);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    public Response delete(PermissaoRoleEntity role) {
        try {
            permissaoRoleController.delete(role);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idPermissao}")
    public Response deleteById(@PathParam("idPermissao") String idPermissao) {
        try {
            permissaoRoleController.deleteById(Integer.parseInt(idPermissao));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
