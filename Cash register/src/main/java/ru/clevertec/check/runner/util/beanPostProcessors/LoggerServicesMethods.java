package ru.clevertec.check.runner.util.beanPostProcessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.util.beanPostProcessors.handlers.DynamicHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggerServicesMethods implements BeanPostProcessor {


    private static final Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Service.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(), beanClass.getInterfaces(), new DynamicHandler(bean));
        }
        return bean;
    }
}
