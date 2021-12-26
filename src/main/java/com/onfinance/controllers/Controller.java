package com.onfinance.controllers;

import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.Repository;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 * @param <PK>
 * @param <T>
 */
public abstract class Controller<PK extends Serializable, T> {

    private final Class<? extends Repository> repository;

    public Controller(Class<? extends Repository> repository) {
        this.repository = repository;
    }

    public List<T> findAll() throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            List<T> list = session.get(repository).findAll();
            return list;
        }
    }

    public T findById(PK id) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            T object = (T) session.get(repository).findById(id);
            return object;
        }
    }

    public void save(T object) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(repository).saveNewTransaction(object);
        }
    }

    public void saveAll(List<T> objects) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(repository).saveAllNewTransaction(objects);
        }
    }

    public void update(T object) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(repository).updateNewTransaction(object);
        }
    }

    public void updateAll(List<T> objects) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(repository).updateAllNewTransaction(objects);
        }
    }

    public void deleteById(PK id) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            T object = (T) session.get(repository).findById(id);
            session.get(repository).deleteNewTransaction(object);
        }
    }

    public void delete(T object) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(repository).deleteNewTransaction(object);
        }
    }

    public void deleteAll(List<T> objects) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(repository).deleteAllNewTransaction(objects);
        }
    }

}
