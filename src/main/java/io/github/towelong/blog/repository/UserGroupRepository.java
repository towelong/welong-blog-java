/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:04
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    UserGroup findByUserId(Long uid);

    @Transactional
    @Modifying
    @Query(value = "insert into user_group(user_id, group_id) values (?1,?2)",nativeQuery = true)
    void nativeSave(Long uid,Long gid);


    @Transactional
    @Modifying
    @Query(value = "update user_group set group_id=?1 where user_id=?2",nativeQuery = true)
    void editUserGroup(Long gid,Long uid);

    @Transactional
    @Modifying
    @Query(value = "update user_group set delete_time = ?1 where user_id = ?2",nativeQuery = true)
    void deleteUserGroup(Date date, Long uid);

    @Transactional
    @Modifying
    @Query(value = "update user_group set delete_time = ?1 where group_id = ?2",nativeQuery = true)
    void deleteUserGroupByGroupId(Date date, Long gid);

    List<UserGroup> findAllByGroupId(Long groupId);
}
