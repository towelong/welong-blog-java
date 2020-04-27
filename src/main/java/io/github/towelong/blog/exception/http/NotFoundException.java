/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/3/11 10:24
 */
package io.github.towelong.blog.exception.http;

public class NotFoundException extends HttpException{
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }

}
