/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/18 9:52
 */
package io.github.towelong.blog.interceptor;

import io.github.towelong.blog.annotation.Required;
import io.github.towelong.blog.exception.http.ForbiddenException;
import io.github.towelong.blog.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Annotation[] annotations = method.getAnnotations();

            Required required = this.getLevel(annotations);
            if (required == null) {
                // 不加权限注解 默认该接口可以访问
                return true;
            } else {
                String header = request.getHeader("Authorization");
                if (!(header != null && header.length() != 0)) {
                    throw new ForbiddenException(10004);
                }
                if (header.startsWith("Bearer")) {
                    String token = header.replace("Bearer ", "");
                    if (jwtUtils.VerifyToken(token)) {
                        return this.getUserLevel(required.scope(), (Long) request.getAttribute("scope"));
                    }
                    throw new ForbiddenException(10004);
                }
                throw new ForbiddenException(10004);
            }
        }
        return true;
    }

    private boolean getUserLevel(long scope, long user_scope) {
        if (user_scope >= scope) {
            return true;
        }
        throw new ForbiddenException(20005);
    }

    // 获取注解上的权限数字
    private Required getLevel(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> aClass = annotation.annotationType();
            Required required = aClass.getAnnotation(Required.class);
            if (required != null){
                return required;
            }
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
