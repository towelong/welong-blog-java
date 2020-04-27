/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:18
 */
package io.github.towelong.blog.VO;

import io.github.towelong.blog.BO.TagBO;
import io.github.towelong.blog.model.Article;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagArticleVO {
    private Long id;
    private TagBO tag;
    private Article article;

}
