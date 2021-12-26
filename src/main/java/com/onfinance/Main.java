package com.onfinance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onfinance.entities.PeriodicActionEntity;
import com.onfinance.hibernate.HibernateSession;
import com.onfinance.repositories.FaturaRepository;
import com.onfinance.repositories.PeriodicActionRepository;
import com.onfinance.utils.LogUtil;
import com.onfinance.utils.XmlQueries;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author Vanderlei Fiorentin
 */
public class Main {

    public static void main(String[] args) {

        final ResourceConfig resourceConfig = new ResourceConfig(ApplicationConfig.class);
        resourceConfig.packages("com.onfinance");
        resourceConfig.register(MultiPartFeature.class);
        resourceConfig.register(JacksonJsonProvider.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        Server server = new Server(8081);

        ServletHolder serHol = new ServletHolder(new ServletContainer(resourceConfig));
        serHol.setInitOrder(1);
        serHol.setInitParameter("jersey.config.server.provider.packages", "com.onfinance");

        ServletContextHandler ctx = new ServletContextHandler(server, "/");
        ctx.addServlet(serHol, "/*");
        ctx.setContextPath("/");

        server.setHandler(ctx);

        try {
            criarPeriodicActions();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
        }

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
        } finally {
            server.destroy();
        }

    }

    private static void criarPeriodicActions() throws Exception {

        LogUtil.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{LocalDateTime.now(), "Criando as Periodics Actions ...."});

        List<PeriodicActionEntity> periodics;
        
        try ( HibernateSession session = HibernateSession.getHibernateSession()) {
            periodics = session.get(PeriodicActionRepository.class).findByStatus(true);
        }

        Timer timer = new Timer();

        periodics.forEach(p -> {
            try {
                Class<?> clazz = Class.forName("com.onfinance.periodics." + p.getNome());
                Object periodic = clazz.getConstructor(PeriodicActionEntity.class).newInstance(p);
                Method execute = periodic.getClass().getMethod("execute");

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            try {
                                execute.invoke(periodic);
                            } catch (IllegalArgumentException | InvocationTargetException ex) {
                                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
                            }
                        } catch (IllegalAccessException ex) {
                            LogUtil.getLogger().log(Level.SEVERE, "{0}: {1}", new Object[]{LocalDateTime.now(), ex});
                        }
                    }
                }, p.getDelay(), p.getIntervalo());

                LogUtil.getLogger().log(Level.INFO, "{0}: {1} {2} {3}", new Object[]{LocalDateTime.now(), "Periodic", p.getNome(), "criada com sucesso!"});

            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LogUtil.getLogger().log(Level.SEVERE, "{0}: {1} {2} \n {3}", new Object[]{LocalDateTime.now(), "Erro ao criar a action", p.getNome(), ex});
            }
        });

    }
}
