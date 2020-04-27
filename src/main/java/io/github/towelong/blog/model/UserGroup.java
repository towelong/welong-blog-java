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
@Table(name = "user_group")
public class UserGroup extends BaseModel{
    @Id
    private Long id;
    private Long userId;
    private Long groupId;
}
