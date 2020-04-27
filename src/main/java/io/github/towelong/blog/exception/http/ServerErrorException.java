/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/3/15 12:13
 */
package io.github.towelong.blog.exception.http;

public class ServerErrorException extends HttpException {
    public ServerErrorException(int code) {
        this.httpStatusCode = 500;
        this.code = code;
    }
}
