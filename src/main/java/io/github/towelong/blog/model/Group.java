/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 18:33
 */
package io.github.towelong.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "[group]")
public class Group {
    @Id
    private Long id;
    private String groupName;
    private Long scope;

    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
}
