package com.onfinance.resources;

import com.onfinance.utils.GerenciadorArquivosUtil;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

/**
 *
 * @author Vanderlei Fiorentin
 */
@Path("api/files")
public class FileResource {

    private static String UPLOAD_PATH;

    public FileResource() {
        UPLOAD_PATH = PropertyUtil.get("com.onfinance.files");
    }

    @GET
    @Path("{file}")
    public Response download(@PathParam("file") String fileName) {
        try {
            File file = new File(UPLOAD_PATH + fileName);
            String mimeType = Files.probeContentType(file.toPath());
            return Response.status(Response.Status.OK).entity(file).header(mimeType, file.getName()).build();
        } catch (IOException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

    @GET
    @Path("files/{idLancto}")
    public Response getFileByLancto(@PathParam("idLancto") String idLancto) {
        try {
            GerenciadorArquivosUtil arquivos = new GerenciadorArquivosUtil();
            HttpResponse response = arquivos.getFileByLancto("1kSgRbRk86ldxynQf_NqhRIb7WP2wcX-W");
            //System.out.println(stream.);
            //String mimeType = Files.probeContentType(multiPart.);
            return Response.status(Response.Status.OK).entity(null).build();
        } catch (IOException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }
    }

}
