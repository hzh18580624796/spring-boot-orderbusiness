package com.hzh.springbootorderbusiness.orderbusiness.core;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BusinessEventListener {

    @AliasFor(annotation = Component.class)
    String value() default "";

    String businessCode() default "default";

    String event() default "";

    String status() default "";
}

//String nextEvent() default "";
