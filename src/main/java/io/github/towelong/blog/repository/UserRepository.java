/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/16 11:49
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByAccount(String account);

    @Transactional
    @Modifying
    @Query(value = "insert into user(nickname, account, password) values(?1,?2,?3) ",nativeQuery = true)
    void nativeSave(String nickname,String account,String password);

    User findByAccount(String account);

    @Transactional
    @Modifying
    @Query(value = "update user set nickname=?1,account=?2 where id=?3",nativeQuery = true)
    void editUser(String nickname,String account,Long id);

    @Transactional
    @Modifying
    @Query(value = "update user set password=?1 where id=?2",nativeQuery = true)
    void editUserPassword(String password,Long id);


    @Transactional
    @Modifying
    @Query(value = "update user set delete_time = ?1 where id = ?2",nativeQuery = true)
    void deleteUser(Date date, Long uid);
}
