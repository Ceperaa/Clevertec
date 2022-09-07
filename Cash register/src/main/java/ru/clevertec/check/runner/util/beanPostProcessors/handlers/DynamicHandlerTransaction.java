package ru.clevertec.check.runner.util.beanPostProcessors.handlers;

import ru.clevertec.check.runner.repository.impl.jdbc.connector.EntityManager;
import ru.clevertec.check.runner.util.beanPostProcessors.annotations.Transactional;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

public class DynamicHandlerTransaction implements InvocationHandler {

    private final Object bean;
    private final EntityManager entityManager;
    private final Class<?> originClass;

    public DynamicHandlerTransaction(Object bean, EntityManager entityManager, Class<?> originClass) {
        this.bean = bean;
        this.entityManager = entityManager;
        this.originClass = originClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = entityManager.getConnect();
        try {
            Method method1 = originClass.getMethod(method.getName(), method.getParameterTypes());
            if (method1.isAnnotationPresent(Transactional.class)) {
                int isolationLevel = method1.getAnnotation(Transactional.class).isolation();
                boolean autoCommit = connection.getAutoCommit();
                if (autoCommit) {
                    connection.setTransactionIsolation(isolationLevel);
                }
                connection.setAutoCommit(false);
                Object retVal = method.invoke(bean, args);
                connection.commit();
                if (autoCommit) {
                    connection.setAutoCommit(true);
                    entityManager.close();
                }
                return retVal;
            } else {
                return method.invoke(bean, args);
            }
        } catch (InvocationTargetException e) {
            connection.rollback();
            e.printStackTrace();
            throw e.getCause();
        }
    }
}
