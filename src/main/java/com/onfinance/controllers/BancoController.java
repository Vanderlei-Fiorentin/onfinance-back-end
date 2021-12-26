package com.onfinance.controllers;

import com.onfinance.entities.BancoEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.BancoRepository;
import com.onfinance.utils.FileUtil;
import com.onfinance.utils.HashMD5;
import com.onfinance.utils.PropertyUtil;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class BancoController extends Controller<Integer, BancoEntity> {

    private final String UPLOAD_PATH;

    public BancoController() {
        super(BancoRepository.class);
        UPLOAD_PATH = PropertyUtil.get("com.onfinance.files");
    }

    public void save(Optional<FormDataBodyPart> formData, BancoEntity banco) throws Exception {
        if (formData.isPresent()) {
            String logo = saveFile(formData.get(), null);
            banco.setLogo(logo);
        }
        try (HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(BancoRepository.class).saveNewTransaction(banco);
        }
    }

    public void update(Optional<FormDataBodyPart> formData, BancoEntity banco) throws Exception {
        if (formData.isPresent()) {
            String logo = saveFile(formData.get(), banco.getLogo());
            banco.setLogo(logo);
        }
        try (HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(BancoRepository.class).updateNewTransaction(banco);
        }
    }

    public void remove(BancoEntity banco) throws Exception {
        if (!Objects.isNull(banco.getLogo())) {
            new FileUtil().remove(UPLOAD_PATH, banco.getLogo());
        }
        try (HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(BancoRepository.class).deleteNewTransaction(banco);
        }
    }

    private String getFileName(FormDataBodyPart formData) throws NoSuchAlgorithmException, IOException {
        FormDataContentDisposition file = formData.getFormDataContentDisposition();
        String extensao = file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());
        String fileName = file.getFileName() + LocalDateTime.now();
        return new HashMD5().hashing(fileName).concat(extensao).toLowerCase();
    }

    private String saveFile(FormDataBodyPart formData, String filename) throws NoSuchAlgorithmException, IOException {
        FileUtil fileUtil = new FileUtil();
        fileUtil.remove(UPLOAD_PATH, filename);
        String hashFileName = getFileName(formData);
        InputStream fileInputStream = formData.getValueAs(InputStream.class);
        fileUtil.save(UPLOAD_PATH, fileInputStream, hashFileName);
        return hashFileName;
    }

}
