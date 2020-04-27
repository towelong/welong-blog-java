/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 19:04
 */
package io.github.towelong.blog.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserAdminInfoVO {
    private Long id;
    private String groupName;
    private String nickname;
    private String email;
    private String account;
    private String password;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
}
