/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/16 11:51
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into tag(tag_name) values (?1)",nativeQuery = true)
    void insertTag(String name);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    Long getCurrentTid();

    List<Tag> findAllByOrderByCreateTimeDesc();

    @Transactional
    @Modifying
    @Query(value = "update tag set tag_name = ?1 where id = ?2",nativeQuery = true)
    void updateTag(String tag_name,Long tid);

    @Transactional
    @Modifying
    @Query(value = "update tag set delete_time = ?1 where id = ?2",nativeQuery = true)
    void deleteTag(Date date, Long tid);


}
