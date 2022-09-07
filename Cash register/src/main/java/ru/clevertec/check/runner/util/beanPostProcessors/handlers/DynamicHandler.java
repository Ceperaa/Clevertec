package ru.clevertec.check.runner.util.beanPostProcessors.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.runner.util.entity.LogObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DynamicHandler implements InvocationHandler {

    private final Object bean;
    private final Gson gson;

    public DynamicHandler(Object bean) {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        this.bean = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger logger = LogManager.getLogger(bean.getClass());
        try {
            Object retVal = method.invoke(bean, args);
            logger.debug(gson.toJson(
                    LogObject
                            .builder()
                            .methodName(method.getName())
                            .params(Arrays.toString(args))
                            .returnParam(retVal)
                            .build()
            ));
            return retVal;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e.getCause();
        }
    }
}
