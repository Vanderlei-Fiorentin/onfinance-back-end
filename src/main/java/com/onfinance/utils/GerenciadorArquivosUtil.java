package com.onfinance.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.dtos.LoginDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import org.apache.http.ParseException;
import java.net.http.HttpResponse;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class GerenciadorArquivosUtil {

    public HttpResponse send(File file, String fileName, String token, int timeout) throws Exception {

        String folder = PropertyUtil.get("com.google.drive.documentos");
        String uri = String.format("%s/%s/%s/%s/%s", ServicePath.GERENCIADOR_ARQUIVOS, "upload", 2, folder, fileName);

        LogUtil.getLogger().log(Level.INFO, "{0}: Requisição para: {1}", new Object[]{LocalDateTime.now(), uri});

        try {
            InputStream inputStream = new FileInputStream(file);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofInputStream(() -> inputStream))
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/octet-stream")
                    .header("Authorization", String.format("Bearer %s", token))
                    .timeout(Duration.ofSeconds(timeout))
                    .build();

            return HttpUtil.post(request, timeout);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao realizar upload para o Google Drive!", ex});
            throw new Exception(ex);
        }
    }

    public HttpResponse login() throws JsonProcessingException, FileNotFoundException {

        String uri = String.format("%s/%s", ServicePath.GERENCIADOR_ARQUIVOS, "login");

        LogUtil.getLogger().log(Level.INFO, "{0}: Requisição para: {1}", new Object[]{LocalDateTime.now(), uri});

        try {

            String login = PropertyUtil.get("com.gerenciadorarquivos.login");
            String password = PropertyUtil.get("com.gerenciadorarquivos.password");

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

    public HttpResponse getFileByLancto(String fileId) throws JsonProcessingException, FileNotFoundException {

        String token = (String) login().body();

        String uri = String.format("%s/%s/%s", ServicePath.GERENCIADOR_ARQUIVOS, "files/id", fileId);

        LogUtil.getLogger().log(Level.INFO, "{0}: Requisição para: {1}", new Object[]{LocalDateTime.now(), uri});

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Bearer %s", token))
                .timeout(Duration.ofSeconds(15))
                .build();

        return HttpUtil.post(request, 15);
    }

}
