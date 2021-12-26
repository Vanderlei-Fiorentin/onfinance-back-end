package com.onfinance.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.dtos.EmailDto;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import org.apache.http.ParseException;
import java.net.http.HttpResponse;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class EmailServiceUtil {

    public HttpResponse send(EmailDto email, int timeout) throws JsonProcessingException {

        LogUtil.getLogger().log(Level.INFO, "{0}: Requisição para: {1}", new Object[]{LocalDateTime.now(), ServicePath.EMAIL_SERVICE});

        String json = new ObjectMapper().writeValueAsString(email);

        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(ServicePath.EMAIL_SERVICE))
                .headers("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(timeout))
                .build();

        try {
            return HttpUtil.post(request, timeout);
        } catch (ParseException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao enviar e-mail!", ex});
        }
        return null;
    }

}
