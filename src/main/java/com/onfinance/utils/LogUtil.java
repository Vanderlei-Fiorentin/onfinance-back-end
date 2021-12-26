package com.onfinance.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class LogUtil {
    
    private static LogUtil instance = null; 
    private static final Logger logger = Logger.getLogger(LogUtil.class.getName()); 
    private FileHandler file = null;

    public LogUtil() { 
        String path = PropertyUtil.get("com.onfinance.logs");
        try {                      
            file = new FileHandler(path + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ".log");
        } catch (IOException | SecurityException ex) {
            System.err.println(ex);
        }
        file.setFormatter(new SimpleFormatter());
        file.setLevel(Level.FINE);
        logger.addHandler(file);
    }
    
    public static Logger getLogger() {
        if(instance == null) {
            instance = new LogUtil();
            LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Instanciando a classe Logger ...."});
        }
        return logger;
    }
    
}