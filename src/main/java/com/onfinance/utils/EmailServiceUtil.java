package com.onfinance.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.dtos.EmailDto;
import com.onfinance.dtos.LoginDto;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpRequest;
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

    public HttpResponse send(EmailDto email, String token, int timeout) throws JsonProcessingException {

        String uri = String.format("%s/%s", ServicePath.EMAIL_SERVICE, "sending-email");
        LogUtil.getLogger().log(Level.INFO, "{0}: Requisição para: {1}", new Object[]{LocalDateTime.now(), uri});

        String json = new ObjectMapper().writeValueAsString(email);

        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(uri))
                .headers("Content-Type", "application/json")
                .headers("Authorization", String.format("Bearer %s", token))
                .timeout(Duration.ofSeconds(timeout))
                .build();

        try {
            return HttpUtil.post(request, timeout);
        } catch (ParseException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao enviar e-mail!", ex});
        }
        return null;
    }

    public HttpResponse login() throws JsonProcessingException, FileNotFoundException {

        String uri = String.format("%s/%s", ServicePath.EMAIL_SERVICE, "login");

        LogUtil.getLogger().log(Level.INFO, "{0}: Requisição para: {1}", new Object[]{LocalDateTime.now(), uri});

        try {

            String login = PropertyUtil.get("email.service.login");
            String password = PropertyUtil.get("email.service.password");

            LoginDto loginMod = new LoginDto(login, password);
            String json = new ObjectMapper().writeValueAsString(loginMod);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .build();

            return HttpUtil.post(request, 5);
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao realizar a autenticação!", ex});
        }
        return null;
    }

}
