package org.grootsec.scubbs.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
//        packages("org.grootsec.scubbs.controller");
        register(RequestContextFilter.class);
        register(org.grootsec.scubbs.controller.StudentController.class);
        register(org.grootsec.scubbs.controller.TeamController.class);
        register(org.grootsec.scubbs.controller.MessageController.class);
        register(org.grootsec.scubbs.controller.AtController.class);
        register(org.grootsec.scubbs.controller.CriticalController.class);
        register(org.grootsec.scubbs.controller.MarkController.class);
        register(org.grootsec.scubbs.controller.ResumeController.class);
        register(org.grootsec.scubbs.controller.ObjectController.class);
    }

}
