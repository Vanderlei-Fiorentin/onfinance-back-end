package com.onfinance.interceptors;

import com.onfinance.UserSession;
import com.onfinance.controllers.JwtController;
import com.onfinance.utils.Headers;
import com.onfinance.utils.LogUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {

        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());
            return;
        }

        UserSession session = UserSession.getCurrentInstance();
        JwtController jwt = new JwtController();
        boolean recursoLivreAcesso = false;

        String token = requestCtx.getHeaderString(Headers.AUTHORIZATION);
        String path = requestCtx.getUriInfo().getPath();

        LogUtil.getLogger().log(Level.INFO, "Recurso acessado: {0}", path);

        for (String recurso : UserSession.recursosLivreAcesso) {
            if (path.startsWith(recurso)) {
                recursoLivreAcesso = true;
                break;
            }
        }

        if (!recursoLivreAcesso) {

            if (token == null || token.equals("")) {
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            try {
                if (!jwt.isExpired(token)) {
                    session.decodeToken(token);
                    if (session.hasAcesso(path, requestCtx.getMethod(), session.getRoles())) {
                        if (jwt.isExpired(token)) {
                            requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                        }
                    } else {
                        LogUtil.getLogger().log(Level.WARNING, "{0}: {1} {2} {3}", new Object[]{LocalDateTime.now(), "Usuário", session.getUsuario().getUsuario(), "sem acesso ao recurso!"});
                        requestCtx.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                    }
                } else {
                    requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            } catch (Exception ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
                requestCtx.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            }
        }

    }
}
