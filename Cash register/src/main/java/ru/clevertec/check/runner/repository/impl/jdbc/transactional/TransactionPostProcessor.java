package ru.clevertec.check.runner.repository.impl.jdbc.transactional;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionPostProcessor implements BeanPostProcessor {

    private static final Map<String, Class<?>> map = new HashMap<>();
    private final EntityManager entityManager;


    public TransactionPostProcessor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();


        for (Method method : beanClass.getMethods()) {
            if (method.isAnnotationPresent(Transactional.class)) {
                map.put(beanName, beanClass);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader()
                    , interfacesArray(beanClass)
                    , new DynamicHandlerTransaction(bean, entityManager, beanClass));
        }
        return bean;
    }

    private Class<?>[] interfacesArray(Class<?> beanClass) {
        Class<?>[] interfaces = beanClass.getInterfaces();
        Class<?>[] interfacesSupperClasses = beanClass.getSuperclass().getInterfaces();
                if (interfaces.length==0) {
                    interfaces = interfacesSupperClasses;
        }
        return interfaces;
    }
}
