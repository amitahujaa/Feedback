package com.onlyflights.source;

import com.onlyflights.source.models.AccessLevel;
import com.onlyflights.source.repositories.AccessLevelRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

@SpringBootApplication
public class BootUpService {

	public static void main(String[] args) {
		SpringApplication.run(BootUpService.class, args);
	}

	@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(80);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }
	
	@Bean
	CommandLineRunner init(AccessLevelRepo accessLevelRepo) {

	    return args -> {

	        AccessLevel adminAccessLevel = accessLevelRepo.findByAccessLevel("ADMIN");
	        if (adminAccessLevel == null) {
	            AccessLevel access = new AccessLevel();
	            access.setAccessLevel("ADMIN");
	            accessLevelRepo.save(access);
	        }

	        AccessLevel userAccessLevel = accessLevelRepo.findByAccessLevel("USER");
	        if (userAccessLevel == null) {
	            AccessLevel newAccess = new AccessLevel();
	            newAccess.setAccessLevel("USER");
	            accessLevelRepo.save(newAccess);
	        }
	    };

	}

}
