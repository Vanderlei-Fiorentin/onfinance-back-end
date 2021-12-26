package com.onfinance.repositories;

import com.onfinance.entities.RoleEntity;
import com.onfinance.entities.UsuarioEntity;
import com.onfinance.utils.XmlQueries;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class RoleRepository extends Repository<Integer, RoleEntity> {

    private final XmlQueries queries;
    
    public RoleRepository() {
        super();
        this.queries = new XmlQueries("RoleQueries.xml");
    }

    public RoleRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("RoleQueries.xml");
    }

    public List<String> findByUsuario(UsuarioEntity usuario) {        
        return query(queries.get("findByUsuario"), Map.ofEntries(entry("usuario", usuario))).getResultList();
    }

}
