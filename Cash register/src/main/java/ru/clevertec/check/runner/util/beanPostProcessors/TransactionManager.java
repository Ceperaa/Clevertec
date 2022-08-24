package ru.clevertec.check.runner.util.beanPostProcessors;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
@AllArgsConstructor
public class TransactionManager implements BeanPostProcessor {

//    private static final Map<String, Class<?>> map = new HashMap<>();
//    private final EntityManager entityManager;
//
//    @SneakyThrows
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        Class<?> beanClass = bean.getClass();
//
//
//        for (Method method : beanClass.getMethods()) {
//            if (method.isAnnotationPresent(Transactional.class)) {
//                map.put(beanName, beanClass);
//            }
//        }
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        Class<?> beanClass = map.get(beanName);
//        if (beanClass != null) {
//            return Proxy.newProxyInstance(
//                    beanClass.getClassLoader(),
//                    interfacesArray(beanClass),
//                    new DynamicHandlerTransaction(bean, entityManager, beanClass));
//        }
//        return bean;
//    }
//
//    private Class<?>[] interfacesArray(Class<?> beanClass) {
//        Class<?>[] interfaces = beanClass.getInterfaces();
//        Class<?>[] interfacesSupperClasses = beanClass.getSuperclass().getInterfaces();
//        if (interfaces.length == 0) {
//            interfaces = interfacesSupperClasses;
//        }
//        return interfaces;
//    }
}
