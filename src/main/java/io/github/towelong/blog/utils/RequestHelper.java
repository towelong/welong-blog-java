/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/3/11 11:28
 */
package io.github.towelong.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static String getRequestUrl() {
        String methods = getRequest().getMethod();
        return methods + " " + getRequest().getServletPath();
    }

    public static long getUid() {
        return (long)RequestHelper.getRequest().getAttribute("uid");
    }
    public static long getScope() {
        return (long)RequestHelper.getRequest().getAttribute("scope");
    }
}
