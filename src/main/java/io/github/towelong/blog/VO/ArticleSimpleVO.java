/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/20 12:17
 */
package io.github.towelong.blog.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleSimpleVO {

    private Long id;
    private String title;
    private String description;
    private String image;
    private boolean status;
    private Long views;
    private Long like;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
}
