package com.onfinance.controllers;

import com.onfinance.dtos.UsuarioDto;
import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.entities.RoleEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.entities.UsuarioRoleEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.PerfilUsuarioRepository;
import com.onfinance.repositories.UsuarioRepository;
import com.onfinance.repositories.UsuarioRoleRepository;
import com.onfinance.utils.HashMD5;
import com.onfinance.utils.LogUtil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UsuarioController extends Controller<Integer, UsuarioEntity> {

    public UsuarioController() {
        super(UsuarioRepository.class);
    }

    public UsuarioDto findByIdUsuario(Integer id) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            UsuarioEntity usuario = session.get(UsuarioRepository.class).findById(id);
            PerfilUsuarioEntity perfil = session.get(PerfilUsuarioRepository.class).findByIdUsuario(id).get();
            List<UsuarioRoleEntity> roles = session.get(UsuarioRoleRepository.class).findByUsuario(id);
            return new UsuarioDto(usuario, perfil, roles);
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public UsuarioEntity findByEmail(String email) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            UsuarioEntity usuario = session.get(UsuarioRepository.class).findByEmail(email)
                    .orElseThrow(() -> new Exception("Email não localizado na base de dados!"));
            return usuario;
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }
    }

    public void save(UsuarioEntity usuario, PerfilUsuarioEntity perfil, Optional<FormDataBodyPart> foto, List<RoleEntity> roles) throws Exception {

        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new Exception("É obrigatório informar o campo senha!");
        }

        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            List<UsuarioRoleEntity> usuariosRoles = new ArrayList<>();
            String senha = new HashMD5().hashing(usuario.getSenha()).toLowerCase();
            usuario.setSenha(senha);
            // Atualiza o usuário
            session.openTransaction();
            session.get(UsuarioRepository.class).save(usuario);
            new PerfilUsuarioController().save(usuario, perfil, foto, session);
            // Inclui as roles
            roles.forEach(role -> {
                usuariosRoles.add(new UsuarioRoleEntity(usuario, role));
            });
            new UsuarioRoleController().atualizarRoles(usuario, usuariosRoles, session);
            session.commit();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2}", new Object[]{LocalDateTime.now(), "Erro ao salvar o usuário!", ex});
            throw new Exception(ex);
        }
    }

    public void update(UsuarioEntity usuario, PerfilUsuarioEntity perfil, Optional<FormDataBodyPart> foto, List<RoleEntity> roles) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            
            List<UsuarioRoleEntity> usuariosRoles = new ArrayList<>();            
            String senha;
            
            if (usuario.getSenha() != null) {
                senha = new HashMD5().hashing(usuario.getSenha()).toLowerCase();
            } else {
                senha = session.get(UsuarioRepository.class).findById(usuario.getId()).getSenha();
            }
            
            usuario.setSenha(senha);
            // Atualiza o usuário
            session.openTransaction();
            session.get(UsuarioRepository.class).update(usuario);
            new PerfilUsuarioController().update(usuario, perfil, foto, session);
            // Inclui as roles
            roles.forEach(role -> {
                usuariosRoles.add(new UsuarioRoleEntity(usuario, role));
            });
            new UsuarioRoleController().atualizarRoles(usuario, usuariosRoles, session);
            session.commit();
        } catch (IOException | NoSuchAlgorithmException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} \n {2}", new Object[]{LocalDateTime.now(), "Erro ao atualizar o usuário!", ex});
            throw new Exception(ex);
        }
    }

    public void remove(int idUsuario) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {

            UsuarioEntity usuario = session.get(UsuarioRepository.class).findById(idUsuario);
            Optional<PerfilUsuarioEntity> perfil = session.get(PerfilUsuarioRepository.class).findByUsuario(usuario.getUsuario());
            List<UsuarioRoleEntity> usuariosRoles = session.get(UsuarioRoleRepository.class).findByUsuario(idUsuario);

            session.openTransaction();
            session.get(UsuarioRoleRepository.class).deleteAll(usuariosRoles);

            if (perfil.isPresent()) {
                new PerfilUsuarioController().remove(perfil.get(), session);
            }

            session.get(UsuarioRepository.class).delete(usuario);
            session.commit();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
            throw new Exception(ex);
        }

    }

    public void alterarSenha(UsuarioEntity usuario) throws Exception {
        String senha = new HashMD5().hashing(usuario.getSenha()).toLowerCase();
        usuario.setSenha(senha);
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            session.get(UsuarioRepository.class).updateNewTransaction(usuario);
        }
    }

}
