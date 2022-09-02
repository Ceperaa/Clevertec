package ru.clevertec.check.runner.util.beanPostProcessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.configuration.AnnotationConfigApplicationContextInitializer;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Servlet;

@Component
public class ServletRegistration implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Servlet.class)) {
            String url = beanClass.getAnnotation(Servlet.class).url();
            AnnotationConfigApplicationContextInitializer
                    .getContext()
                    .addServlet(beanName, (javax.servlet.Servlet) bean).addMapping(url);
        }
        return bean;
    }
}
