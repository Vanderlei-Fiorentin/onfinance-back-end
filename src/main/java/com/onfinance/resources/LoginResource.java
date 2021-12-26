package com.onfinance.resources;

import com.onfinance.controllers.JwtController;
import com.onfinance.controllers.LoginController;
import com.onfinance.dtos.JwtDto;
import com.onfinance.dtos.LoginDto;
import com.onfinance.utils.Headers;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path(ServicePath.LOGIN)
public class LoginResource {

    private final LoginController loginController;

    public LoginResource() {
        loginController = new LoginController();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDto login) {
        try {
            JwtDto token = loginController.login(login);
            if (token != null) {
                return Response.status(Response.Status.OK).entity(token).build();
            }
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();
    }

    @POST
    @Path("redefinir-senha")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response redefinirSenha(String email) {
        try {
            loginController.redefinirSenha(email);
            return Response.status(Response.Status.OK).entity("").build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
    
    @PUT
    @Path("alterar-senha")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response alterarSenha(String senha, @HeaderParam(Headers.KEY) String jwtToken) {
        try {            
            JwtController jwtController = new JwtController();
            if(jwtController.isExpired(jwtToken)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();
            }
            loginController.alterarSenha(jwtToken, senha);
            return Response.status(Response.Status.OK).entity("").build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }
}