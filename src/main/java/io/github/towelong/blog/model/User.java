/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/15 18:13
 */
package io.github.towelong.blog.model;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.InvalidHashException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String account;
    private String password;

    /**
     * 设置密文密码
     *
     * @param password 原始密码
     */
    public String setPasswordEncrypt(String password) {
        char[] chars = password.toCharArray();
        return Hash.password(chars).algorithm(Type.PBKDF2_SHA256).create();
    }

    /**
     *
     * @param password 用户传过来的密码
     * @param cryptPassword 数据库中加密的密码
     * @return boolean
     * @throws InvalidHashException
     */
    public boolean encrypt(String password,String cryptPassword) throws InvalidHashException {
        char[] chars = password.toCharArray();
        return Hash.password(chars).verify(cryptPassword);
    }

}
