package com.onfinance;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Vanderlei Fiorentin
 */
@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages("com.onfinance").register(MultiPartFeature.class).register(JacksonJsonProvider.class);
    }
}
