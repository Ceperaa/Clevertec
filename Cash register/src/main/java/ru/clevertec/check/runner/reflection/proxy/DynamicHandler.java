package ru.clevertec.check.runner.reflection.proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
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
        Object retVal = method.invoke(bean, args);
        Gson gson = new GsonBuilder().create();
        logger.debug(gson.toJson(new LogObject(Arrays.toString(args), retVal)));
        return retVal;
    }
}
