package com.onfinance.hibernate;

import com.onfinance.utils.LogUtil;
import com.onfinance.utils.PropertyUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class HibernateSessionFactory {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
    private static StandardServiceRegistry registry;    

    private static SessionFactory buildSessionFactory() {
        try {
            com.onfinance.hibernate.Environment environment;
            
            boolean production = Boolean.valueOf(PropertyUtil.get("com.onfinance.production"));
            String mensagem = "Sistema rodando em ambiente de ".concat((production) ? "produção" : "teste ...");
            
            LogUtil.getLogger().log(Level.INFO, "{0}: {1} {2}", new Object[]{LocalDateTime.now(), mensagem});
            
            if(production){
                environment = new EnvironmentProd();
            } else {
                environment = new EnvironmentDev();
            }
            
            setSettings(environment);
            Metadata metadata = getSources().getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException ex) {            
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }  

    public final void closeSessionFactory() {
        getSessionFactory().close();
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void setSettings(com.onfinance.hibernate.Environment environment) {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        Map<String, Object> settings = new HashMap<>();
        settings.put(Environment.DIALECT, environment.getDialect());
        settings.put(Environment.DRIVER, environment.getDriver());
        settings.put(Environment.URL, environment.getUrl());
        settings.put(Environment.USER, environment.getUser());
        settings.put(Environment.PASS, environment.getPassword());
        settings.put(Environment.SHOW_SQL, environment.isShowSql());
        settings.put(Environment.FORMAT_SQL, environment.isFormatSql());
        settings.put(Environment.AUTOCOMMIT, environment.isAutoCommit());
        registryBuilder.applySettings(settings);
        registry = registryBuilder.build();
    }

    private static MetadataSources getSources() {
        MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClass(com.onfinance.entities.AgenciaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.BancoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.BandeiraEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.CartaoCreditoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.CategoriaProdutoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.ContaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.DocumentoPagamentoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.DocumentoLanctoContabilEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.EmpresaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.EventoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.EventoLanctoEntradaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.ExtratoBancarioEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.FaturaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.LanctoContabilEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PagamentoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.ParcelaLanctoContabilEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PerfilUsuarioEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PeriodicActionEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PeriodicActionConfiguracaoIntervaloExecucaoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PermissaoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PermissaoRecursoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.PermissaoRoleEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.ProdutoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.ProdutoLanctoSaidaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.RecursoEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.RoleEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.SaidaLanctoEntradaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.TransferenciaBancariaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.UnidadeMedidaEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.UsuarioEntity.class);
        sources.addAnnotatedClass(com.onfinance.entities.UsuarioRoleEntity.class);
        return sources;
    }

}
