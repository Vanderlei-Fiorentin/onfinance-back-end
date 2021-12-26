package com.onfinance.controllers;

import com.onfinance.entities.PermissaoRoleEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.PermissaoRoleRepository;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PermissaoRoleController extends Controller<Integer, PermissaoRoleEntity> {

    public PermissaoRoleController() {
        super(PermissaoRoleRepository.class);
    }

    public List<PermissaoRoleEntity> findByPermissao(int idPermissao) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(PermissaoRoleRepository.class).findByPermissao(idPermissao);
        }
    }

    public List<PermissaoRoleEntity> findByRole(int idRole) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(PermissaoRoleRepository.class).findByRole(idRole);
        }
    }

    @Override
    public void saveAll(List<PermissaoRoleEntity> roles) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            int idRole = roles.get(0).getPermissaoRoleID().getRole().getId();
            session.openTransaction();
            session.get(PermissaoRoleRepository.class).deleteByRole(idRole);
            session.get(PermissaoRoleRepository.class).saveAll(roles);
            session.commit();
        }
    }

}
