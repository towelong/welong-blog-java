/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:40
 */
package io.github.towelong.blog.BO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TagBO {
    private String tagName;
    private Date deleteTime;
}
