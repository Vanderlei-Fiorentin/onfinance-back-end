package com.onfinance.hibernate;

import com.onfinance.utils.LogUtil;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class HibernateSession implements AutoCloseable {

    private final Session session;

    public HibernateSession () {
        session = HibernateSessionFactory.getSessionFactory().openSession();
    }
    
    public static HibernateSession getHibernateSession() {
        return new HibernateSession();
    }

    public void openTransaction() {
        session.getTransaction().begin();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void rollback() {
        session.getTransaction().rollback();
    }

    public void flush() {
        session.flush();
    }    
    
    public Session getSession() {
        return session;
    }

    public <T> T get(Class<T> clazz) {
        try {
            return clazz.getConstructor(Session.class).newInstance(session);
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao instanciar classe", clazz.getName(), ex});
        }
        return null;
    }
    
    @Override
    public void close() {
        session.close();
    }
    
}
