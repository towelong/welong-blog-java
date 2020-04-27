/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:04
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


public interface GroupRepository extends JpaRepository<Group,Long> {

    Group getOneById(Long id);

    List<Group> findAllByOrderByCreateTimeDesc();

    Group findByGroupName(String group_name);


    @Transactional
    @Modifying
    @Query(value = "insert into `group`(group_name, scope) values (?1,?2)",nativeQuery = true)
    void insertGroup(String group_name,Long scope);

    @Transactional
    @Modifying
    @Query(value = "UPDATE `group` SET group_name=?1,scope=?2 WHERE id = ?3",nativeQuery = true)
    void updateGroup(String groupName,Long scope,Long id);

    @Transactional
    @Modifying
    @Query(value = "update `group` set delete_time = ?1 where id = ?2",nativeQuery = true)
    void deleteGroup(Date date, Long gid);
}
