package com.onfinance.repositories;

import com.onfinance.entities.UsuarioRoleEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class UsuarioRoleRepository extends Repository<Integer, UsuarioRoleEntity> {

    private final XmlQueries queries;

    public UsuarioRoleRepository() {
        super();
        this.queries = new XmlQueries("UsuarioRoleQueries.xml");
    }

    public UsuarioRoleRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("UsuarioRoleQueries.xml");
    }

    public List<UsuarioRoleEntity> findByUsuario(Integer idUsuario) {
        return query(queries.get("findByUsuario"), Map.ofEntries(entry("idUsuario", idUsuario))).getResultList();
    }

}
