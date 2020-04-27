/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/15 18:14
 */
package io.github.towelong.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
}
