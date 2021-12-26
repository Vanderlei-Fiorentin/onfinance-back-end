package com.onfinance;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onfinance.controllers.JwtController;
import com.onfinance.entities.RecursoEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.PermissaoRoleRepository;
import com.onfinance.repositories.RecursoRepository;
import com.onfinance.repositories.UsuarioRepository;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UserSession {

    public static List<String> recursosLivreAcesso;
    private static UserSession instance = null;
    private static List<RecursoEntity> recursos;
    private static Map<String, Integer> acoes;
    private UsuarioEntity usuario;
    private List<String> roles;

    public UserSession() {
        acoes = Map.ofEntries(entry("GET", 1), entry("POST", 2), entry("PUT", 3), entry("DELETE", 4));
        recursosLivreAcesso = List.of("api/files", "api/login", "api/redefinir-senha", "api/alterar-senha");
    }

    public boolean hasAcesso(String path, String method, List<String> roles) throws Exception {
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            for (RecursoEntity recurso : getRecursos()) {
                if (path.startsWith(recurso.getRecurso()) && recurso.getAcao() == acoes.get(method)) {
                    return session.get(PermissaoRoleRepository.class).findByRecursoAndRoles(recurso, roles);
                }
            }
        }
        return false;
    }

    public static UserSession getCurrentInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    private List<RecursoEntity> getRecursos() throws Exception {
        if (recursos == null) {
            try ( HibernateSession session = HibernateSession.getHibernateSession()) {
                recursos = session.get(RecursoRepository.class).findAll();
            }
        }
        return recursos;
    }

    public void decodeToken(String token) throws Exception {
        DecodedJWT decode = new JwtController().decodeToken(token);
        List<String> regras = decode.getClaim("Roles").asList(String.class);
        setRoles(regras);
        String nomeUsuario = decode.getClaim("Usuario").asString();
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            UsuarioEntity user = session.get(UsuarioRepository.class).findByUsuario(nomeUsuario)
                    .orElseThrow(() -> new Exception("Usuário não localizado na base de dados!"));
            setUsuario(user);
        }
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public UsuarioEntity getUsuario() {
        return this.usuario;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return this.roles;
    }

}
