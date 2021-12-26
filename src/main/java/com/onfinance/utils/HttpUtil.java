package com.onfinance.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.net.http.HttpResponse;
import java.util.logging.Logger;
import org.apache.http.ParseException;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class HttpUtil {
    
    public static HttpResponse post(HttpRequest request, int timeout) throws JsonProcessingException { 
        try {            
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(timeout))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();    
            
            try {
                return client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao enviar e-mail!", ex});
        }
        return null;
    }

}