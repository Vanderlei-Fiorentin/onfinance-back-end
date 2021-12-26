package com.onfinance.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class FileUtil {

    public void remove(String path, String fileName) {
        if (fileName != null) {
            File file = new File(path + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void save(String path, InputStream fileInputStream, String fileName) {
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            try ( OutputStream out = new FileOutputStream(new File(path.concat(fileName)))) {
                while ((read = fileInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
            }
        } catch (IOException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao salvar o arquivo!", ex});
        }
    }

}
