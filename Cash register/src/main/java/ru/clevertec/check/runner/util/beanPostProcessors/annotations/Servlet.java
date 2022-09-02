package ru.clevertec.check.runner.util.beanPostProcessors.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Servlet {

    @AliasFor(annotation = Component.class)
    String value() default "";

    String url() default "/*";
}
