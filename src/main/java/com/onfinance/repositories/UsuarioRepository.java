package com.onfinance.repositories;

import com.onfinance.entities.UsuarioEntity;
import com.onfinance.utils.XmlQueries;
import java.util.Map;
import static java.util.Map.entry;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsuarioRepository extends Repository<Integer, UsuarioEntity> {

    private final XmlQueries queries;
    
    public UsuarioRepository() {
        super();
        this.queries = new XmlQueries("UsuarioQueries.xml");
    }

    public UsuarioRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("UsuarioQueries.xml");
    }

    public Optional<UsuarioEntity> findByUsuario(String username) {
        Query<UsuarioEntity> query = query(queries.get("findByUsuario"), Map.ofEntries(entry("usuario", username)));
        UsuarioEntity usuario = query.list().isEmpty() ? null : query.getSingleResult();
        return Optional.ofNullable(usuario);
    }

    public Optional<UsuarioEntity> findByEmail(String email) {
        Query<UsuarioEntity> query = query(queries.get("findByEmail"), Map.ofEntries(entry("email", email)));
        UsuarioEntity usuario = query.list().isEmpty() ? null : query.getSingleResult();
        return Optional.ofNullable(usuario);
    }

}
