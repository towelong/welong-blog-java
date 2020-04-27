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
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class Article extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String image;
    private boolean status;
    private Long views;
    private Long like;
    private String content;

//    @OneToMany(mappedBy = "article")
//    private List<ArticleUser> userList;
}
