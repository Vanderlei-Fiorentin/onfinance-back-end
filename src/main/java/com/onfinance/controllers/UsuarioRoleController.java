package com.onfinance.controllers;

import com.onfinance.entities.UsuarioEntity;
import com.onfinance.entities.UsuarioRoleEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.UsuarioRoleRepository;
import java.util.List;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UsuarioRoleController extends Controller<Integer, UsuarioRoleEntity> {

    public UsuarioRoleController() {
        super(UsuarioRoleRepository.class);
    }
    
    public List<UsuarioRoleEntity> findByUsuario(int idUsuario) {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            return session.get(UsuarioRoleRepository.class).findByUsuario(idUsuario);
        }
    }

    public void atualizarRoles(UsuarioEntity usuario, List<UsuarioRoleEntity> roles) {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.openTransaction();
            List<UsuarioRoleEntity> rolesAtuais = session.get(UsuarioRoleRepository.class).findByUsuario(usuario.getId());
            session.get(UsuarioRoleRepository.class).deleteAll(rolesAtuais);
            session.get(UsuarioRoleRepository.class).saveAll(roles);
            session.commit();
        }
    }

    public void atualizarRoles(UsuarioEntity usuario, List<UsuarioRoleEntity> roles, HibernateSession session) {
        List<UsuarioRoleEntity> rolesAtuais = session.get(UsuarioRoleRepository.class).findByUsuario(usuario.getId());
        session.get(UsuarioRoleRepository.class).deleteAll(rolesAtuais);
        session.get(UsuarioRoleRepository.class).saveAll(roles);
    }

}
