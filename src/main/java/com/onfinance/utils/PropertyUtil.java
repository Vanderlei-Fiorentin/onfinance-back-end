package com.onfinance.utils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.Level;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PropertyUtil {

    private static Properties props;

    private static Properties getProperty() {
        try {
            props = new Properties();
            InputStream file = PropertyUtil.class.getResourceAsStream("/config.properties");
            props.load(file);
            return props;
        } catch (IOException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao carregar o arquivo de configuração!", ex});
        }
        return null;
    }

    public static String get(String chave) {
        if (props == null) {
            props = getProperty();
            LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Carregando o config.properties ...."});
        }
        return props.getProperty(chave);
    }
    
    public static Boolean getBoolean(String chave) {
        return Boolean.parseBoolean(get(chave));
    }
    
    public static int getInteger(String chave) {
        return Integer.parseInt(get(chave));
    }
    
    public static long getLong(String chave) {
        return Long.parseLong(get(chave));
    }

}
