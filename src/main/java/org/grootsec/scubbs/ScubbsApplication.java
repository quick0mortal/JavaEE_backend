package org.grootsec.scubbs;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.grootsec.scubbs.config.JerseyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;

import javax.servlet.ServletRegistration;

@SpringBootApplication
public class ScubbsApplication {

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ServletContainer(), "/*");
        registrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registrationBean;
    }

	public static void main(String[] args) {
		SpringApplication.run(ScubbsApplication.class, args);
	}


}
