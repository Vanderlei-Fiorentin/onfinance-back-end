package com.onfinance.repositories;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Vanderlei Fiorentin
 * @param <PK>
 * @param <T>
 */
public abstract class Repository<PK extends Serializable, T> {

    private final Class<T> clazz;
    protected Session session;

    public Repository() {
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public Repository(Session session) {
        this.session = session;
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    public Query query(String sql, Map<String, Object> parameters) {
        Query query = session.createQuery(sql);
        
        parameters.entrySet().forEach(parameter -> {
            query.setParameter(parameter.getKey(), parameter.getValue());
        });
        
        return query;
    }

    public T findById(PK key) {
        T object = session.find(clazz, key);
        return (T) object;
    }

    public List<T> findAll() {
        List<T> list = session.createQuery(("FROM " + clazz.getName())).getResultList();
        return list;
    }
    
    public int findMaxId() {
        List list = session.createQuery("SELECT MAX(id) FROM " + clazz.getName()).getResultList();
        return (list.isEmpty()) ? 0 : ((int) list.get(0));
    }

    public T save(T object) {
        session.persist(object);
        return object;
    }

    public void saveAll(List<T> objects) {
        objects.forEach(object -> {
            session.persist(object);
        });
    }

    public void update(T object) {
        session.merge(object);
    }

    public void updateAll(List<T> objects) {
        objects.forEach(object -> {
            session.merge(object);
        });
    }

    public void delete(T object) {
        session.remove(object);
    }

    public void deleteAll(List<T> objects) {
        objects.forEach(object -> {
            session.remove(object);
        });
    }

    public T saveNewTransaction(T object) {
        session.getTransaction().begin();
        session.persist(object);
        session.getTransaction().commit();
        return object;
    }

    public void saveAllNewTransaction(List<T> objects) {
        session.getTransaction().begin();
        objects.forEach(object -> {
            session.persist(object);
        });
        session.getTransaction().commit();
    }

    public void updateNewTransaction(T object) {
        session.getTransaction().begin();
        session.merge(object);
        session.getTransaction().commit();
    }

    public void updateAllNewTransaction(List<T> objects) {
        session.getTransaction().begin();
        objects.forEach(object -> {
            session.merge(object);
        });
        session.getTransaction().commit();
    }

    public void deleteNewTransaction(T object) {
        session.getTransaction().begin();
        session.remove(object);
        session.getTransaction().commit();
    }

    public void deleteAllNewTransaction(List<T> objects) {
        session.getTransaction().begin();
        objects.forEach(object -> {
            session.remove(object);
        });
        session.getTransaction().commit();
    }

}
