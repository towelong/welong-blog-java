/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/22 11:57
 */
package io.github.towelong.blog.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TagAdminVO {
    private Long id;
    private String tagName;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
}
