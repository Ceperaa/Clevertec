package ru.clevertec.check.util.beanPostProcessors.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.util.entity.LogObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DynamicHandler implements InvocationHandler {

    private final Object bean;

    public DynamicHandler(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger logger = LogManager.getLogger(bean.getClass());
        try {
            Object retVal = method.invoke(bean, args);
            logger.debug(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString((
                    LogObject
                            .builder()
                            .methodName(method.getName())
                            .params(Arrays.toString(args))
                            .returnParam(retVal)
                            .build()
            )));
            return retVal;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e.getCause();
        }
    }
}
