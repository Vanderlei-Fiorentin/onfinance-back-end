package com.onfinance.repositories;

import com.onfinance.entities.PermissaoRoleEntity;
import com.onfinance.entities.RecursoEntity;
import com.onfinance.utils.XmlQueries;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;
import org.hibernate.Session;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class PermissaoRoleRepository extends Repository<Integer, PermissaoRoleEntity> {

    private final XmlQueries queries;

    public PermissaoRoleRepository() {
        super();
        this.queries = new XmlQueries("PermissaoRoleQueries.xml");
    }

    public PermissaoRoleRepository(Session session) {
        super(session);
        this.queries = new XmlQueries("PermissaoRoleQueries.xml");
    }

    public List<String> findByRecurso(RecursoEntity recurso) {
        return query(queries.get("findByRecurso"), Map.ofEntries(entry("idRecurso", recurso.getId()))).getResultList();
    }

    public boolean findByRecursoAndRoles(RecursoEntity recurso, List<String> roles) {
        Map<String, Object> parametros = Map.ofEntries(
                entry("idRecurso", recurso.getId()),
                entry("roles", roles)
        );
        return query(queries.get("findByRecursoAndRoles"), parametros).list().size() > 0;
    }

    public List<PermissaoRoleEntity> findByRole(int idRole) {
        return query(queries.get("findByRole"), Map.ofEntries(entry("idRole", idRole))).getResultList();
    }

    public List<PermissaoRoleEntity> findByPermissao(int idPermissao) {
        return query(queries.get("findByPermissao"), Map.ofEntries(entry("idPermissao", idPermissao))).getResultList();
    }

    public void deleteByRole(int idRole) {
        List<PermissaoRoleEntity> permissoesRoles = findByRole(idRole);
        deleteAll(permissoesRoles);
    }

}
