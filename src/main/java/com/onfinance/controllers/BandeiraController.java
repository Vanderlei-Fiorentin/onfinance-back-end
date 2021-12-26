package com.onfinance.controllers;

import com.onfinance.entities.BandeiraEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.BandeiraRepository;
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
public class BandeiraController extends Controller<Integer, BandeiraEntity> {

    private final String UPLOAD_PATH;

    public BandeiraController() {
        super(BandeiraRepository.class);
        UPLOAD_PATH = PropertyUtil.get("com.onfinance.files");
    }

    public void save(Optional<FormDataBodyPart> formData, BandeiraEntity bandeira) throws Exception {
        if (formData.isPresent()) {
            try {
                String hash = getFileName(formData.get());
                InputStream fileInputStream = formData.get().getValueAs(InputStream.class);
                new FileUtil().save(UPLOAD_PATH, fileInputStream, hash);
                bandeira.setLogo(hash);
            } catch (IOException | NoSuchAlgorithmException ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} /{3}", new Object[]{LocalDateTime.now(), "Erro ao salvar a bandeira!", ex});
                throw new Exception(ex);
            }
        }

        try (HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(BandeiraRepository.class).saveNewTransaction(bandeira);
        }
    }

    public void update(Optional<FormDataBodyPart> formData, BandeiraEntity bandeira) throws Exception {
        if (formData.isPresent()) {

            FileUtil fileUtil = new FileUtil();
            String hash = getFileName(formData.get());

            if (!Objects.isNull(bandeira.getLogo())) {
                fileUtil.remove(UPLOAD_PATH, bandeira.getLogo());
            }

            InputStream fileInputStream = formData.get().getValueAs(InputStream.class);
            fileUtil.save(UPLOAD_PATH, fileInputStream, hash);
            bandeira.setLogo(hash);
        }

        try (HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(BandeiraRepository.class).updateNewTransaction(bandeira);
        }
    }

    public void deleteById(int idBandeira) {
        try (HibernateSession session = HibernateSession.getHibernateSession()) {
            BandeiraEntity bandeira = session.get(BandeiraRepository.class).findById(idBandeira);
            if (!Objects.isNull(bandeira.getLogo())) {
                new FileUtil().remove(UPLOAD_PATH, bandeira.getLogo());
            }
            session.get(BandeiraRepository.class).deleteNewTransaction(bandeira);
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
