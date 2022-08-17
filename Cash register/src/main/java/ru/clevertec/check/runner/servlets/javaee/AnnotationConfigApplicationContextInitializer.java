package ru.clevertec.check.runner.servlets.javaee;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.check.runner.configuration.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AnnotationConfigApplicationContextInitializer implements ServletContextListener {

    private static ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        sce.getServletContext().addServlet("productServlet",applicationContext.getBean(ProductServlet.class)).addMapping("/product/*");
        sce.getServletContext().addServlet("CardServlet",applicationContext.getBean(CardServlet.class)).addMapping("/card/*");
        sce.getServletContext().addServlet("CheckServlet",applicationContext.getBean(CheckServlet.class)).addMapping("/check/*");
    }

    public static ServletContext getContext() {
        return context;
    }
}
