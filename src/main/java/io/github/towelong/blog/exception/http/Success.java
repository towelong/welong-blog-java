/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/18 11:14
 */
package io.github.towelong.blog.exception.http;

public class Success extends HttpException {
    public Success(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}
