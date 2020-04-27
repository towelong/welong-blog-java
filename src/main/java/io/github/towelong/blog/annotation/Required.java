package io.github.towelong.blog.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Required {
    long scope() default 2;
}
