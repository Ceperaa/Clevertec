package ru.clevertec.check.runner.util.beanPostProcessors.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Connection;

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {

    int isolation() default Connection.TRANSACTION_READ_COMMITTED;
}
