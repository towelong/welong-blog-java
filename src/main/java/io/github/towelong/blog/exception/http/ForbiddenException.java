/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/3/11 10:26
 */
package io.github.towelong.blog.exception.http;

public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }


}
