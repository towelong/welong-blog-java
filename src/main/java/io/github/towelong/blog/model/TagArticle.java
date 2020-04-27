/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/15 18:16
 */
package io.github.towelong.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tag_article")
public class TagArticle extends BaseModel{
    @Id
    private Long id;
//    private Long tagId;
//    private Long articleId;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
