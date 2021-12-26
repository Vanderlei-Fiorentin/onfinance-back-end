package com.onfinance.repositories;

import com.onfinance.entities.PerfilUsuarioEntity;
import com.onfinance.utils.XmlQueries;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PerfilUsuarioRepository extends Repository<Integer, PerfilUsuarioEntity> {

    private final XmlQueries queries;
    
    public PerfilUsuarioRepository() {
        super();
        this.queries = new XmlQueries("PerfilUsuarioQueries.xml");
    }

    public PerfilUsuarioRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("PerfilUsuarioQueries.xml");
    }

    public Optional<PerfilUsuarioEntity> findByIdUsuario(int idUsuario) {
        Query<PerfilUsuarioEntity> query = query(queries.get("findByIdUsuario"), Map.ofEntries(entry("idUsuario", idUsuario)));
        PerfilUsuarioEntity perfil = query.list().isEmpty() ? null : query.getSingleResult();
        return Optional.ofNullable(perfil);
    }

    public Optional<PerfilUsuarioEntity> findByUsuario(String usuario) {
        Query<PerfilUsuarioEntity> query = query(queries.get("findByUsuario"), Map.ofEntries(entry("usuario", usuario)));
        PerfilUsuarioEntity perfil = query.list().isEmpty() ? null : query.getSingleResult();
        return Optional.ofNullable(perfil);
    }
}
