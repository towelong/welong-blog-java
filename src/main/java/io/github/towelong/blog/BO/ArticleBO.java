/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/19 14:19
 */
package io.github.towelong.blog.BO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ArticleBO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String content;

    private Long tagId;
}
