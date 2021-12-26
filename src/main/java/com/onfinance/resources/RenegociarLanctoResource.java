package com.onfinance.resources;

import com.onfinance.controllers.RenegociarLanctoController;
import com.onfinance.dtos.RenegociacaoLanctoContabilDto;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.ServicePath;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path(ServicePath.RENEGOCIAR_PARCELAS)
public class RenegociarLanctoResource {
    
    private final RenegociarLanctoController renegociarLanctoController;

    public RenegociarLanctoResource() {
        this.renegociarLanctoController = new RenegociarLanctoController();
    }

    @POST
    public Response quitarLancto(RenegociacaoLanctoContabilDto renegociacao) {
        try {
            renegociarLanctoController.renegociar(renegociacao);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}