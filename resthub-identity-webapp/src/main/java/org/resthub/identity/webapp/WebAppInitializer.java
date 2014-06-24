package org.resthub.identity.webapp;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.getEnvironment().setActiveProfiles("resthub-jpa", "resthub-web-server", "resthub-client-logging", "resthub-identity-group", "resthub-identity-role", "resthub-pool-bonecp");
        String[] locations = {"classpath*:resthubContext.xml", "classpath*:applicationContext.xml", "classpath:boneCPContext.xml"};
        appContext.setConfigLocations(locations);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        Set<String> mappingConflicts = dispatcher.addMapping("/*");
        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException("'dispatcher' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }
        servletContext.addListener(new ContextLoaderListener(appContext));

        servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class).addMappingForUrlPatterns(null, false, "/*");

        //Database Console for managing the app's database (TODO : profile)
        ServletRegistration.Dynamic h2Servlet = servletContext.addServlet("h2console", WebServlet.class);
        h2Servlet.setLoadOnStartup(2);
        h2Servlet.addMapping("/console/database/*");
    }
}