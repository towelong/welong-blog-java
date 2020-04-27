package io.github.towelong.blog.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Required(scope = 16)
public @interface AdminRequired {

}