package com.onfinance.controllers;

import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.PerfilUsuarioRepository;
import com.onfinance.utils.FileUtil;
import com.onfinance.utils.HashMD5;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PerfilUsuarioController extends Controller<Integer, PerfilUsuarioEntity> {

    private final String UPLOAD_PATH;

    public PerfilUsuarioController() {
        super(PerfilUsuarioRepository.class);
        UPLOAD_PATH = PropertyUtil.get("com.onfinance.files");
    }

    public void save(UsuarioEntity usuario, PerfilUsuarioEntity perfil,
            Optional<FormDataBodyPart> foto, HibernateSession session) throws Exception {
        if (foto.isPresent()) {
            InputStream fileInputStream = foto.get().getValueAs(InputStream.class);
            // Salva a foto em disco
            String fileName = getFileName(foto.get());
            new FileUtil().save(UPLOAD_PATH, fileInputStream, fileName);
            perfil.setFoto(fileName);
        }
        perfil.setUsuario(usuario);
        session.get(PerfilUsuarioRepository.class).save(perfil);
    }

    public void update(UsuarioEntity usuario, PerfilUsuarioEntity perfil,
            Optional<FormDataBodyPart> foto, HibernateSession session) throws Exception {
        if (foto.isPresent()) {
            FileUtil fileUtil = new FileUtil();
            InputStream fileInputStream = foto.get().getValueAs(InputStream.class);
            // Remove a foto, caso exista
            if (!Objects.isNull(perfil.getFoto())) {
                fileUtil.remove(UPLOAD_PATH, perfil.getFoto());
            }
            // Salva a foto em disco
            String fileName = getFileName(foto.get());
            fileUtil.save(UPLOAD_PATH, fileInputStream, fileName);
            perfil.setFoto(fileName);
        } else {
            String fotoAtual = session.get(PerfilUsuarioRepository.class).findById(perfil.getId()).getFoto();
            perfil.setFoto(fotoAtual);
        }
        perfil.setUsuario(usuario);
        session.get(PerfilUsuarioRepository.class).update(perfil);
    }

    public void remove(PerfilUsuarioEntity perfil, HibernateSession session) throws Exception {
        FileUtil fileUtil = new FileUtil();
        // Remove a foto, caso exista
        if (!Objects.isNull(perfil.getFoto())) {
            fileUtil.remove(UPLOAD_PATH, perfil.getFoto());
        }
        session.get(PerfilUsuarioRepository.class).delete(perfil);
    }

    public Optional<PerfilUsuarioEntity> findByIdUsuario(int idUsuario) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(PerfilUsuarioRepository.class).findByIdUsuario(idUsuario);
        } catch (NumberFormatException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public String getFileName(FormDataBodyPart form) throws NoSuchAlgorithmException, IOException {
        FormDataContentDisposition file = form.getFormDataContentDisposition();
        String extensao = file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
        String fileName = file.getFileName() + LocalDateTime.now();
        String hash = new HashMD5().hashing(fileName).concat(extensao).toLowerCase();
        return hash;
    }

}
