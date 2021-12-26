package com.onfinance.resources;

import com.onfinance.controllers.RoleController;
import com.onfinance.entities.RoleEntity;
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
@Path(ServicePath.ROLES)
public class RoleResource {

    private final RoleController roleController;

    public RoleResource() {
        this.roleController = new RoleController();
    }

    @GET
    public Response findAll() {
        try {
            List<RoleEntity> roles = roleController.findAll();
            return Response.status(Response.Status.OK).entity(roles).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("{idRole}")
    public Response findById(@PathParam("idRole") String idRole) {
        try {
            RoleEntity role = roleController.findById(Integer.parseInt(idRole));
            return Response.status(Response.Status.OK).entity(role).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @POST
    public Response save(RoleEntity role) {
        try {
            roleController.save(role);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @PUT
    public Response update(RoleEntity role) {
        try {
            roleController.update(role);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    public Response delete(RoleEntity role) {
        try {
            roleController.delete(role);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @DELETE
    @Path("{idRole}")
    public Response deleteById(@PathParam("idRole") String idRole) {
        try {
            roleController.deleteById(Integer.parseInt(idRole));
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
