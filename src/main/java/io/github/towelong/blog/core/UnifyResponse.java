/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/3/11 10:33
 */
package io.github.towelong.blog.core;

import io.github.towelong.blog.utils.RequestHelper;

public class UnifyResponse {
    private int code;
    private String message;
    private String request = RequestHelper.getRequestUrl();


    public UnifyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }
}
