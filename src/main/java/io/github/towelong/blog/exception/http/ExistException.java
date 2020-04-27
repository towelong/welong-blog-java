/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/18 11:11
 */
package io.github.towelong.blog.exception.http;

public class ExistException extends HttpException{

    public ExistException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }
}
