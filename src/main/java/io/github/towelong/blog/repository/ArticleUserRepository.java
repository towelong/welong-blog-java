/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/16 12:31
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.ArticleUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface ArticleUserRepository extends JpaRepository<ArticleUser,Long> {

//    List<ArticleUser> findAllByUserIdOrderByCreateTimeDesc(Long uid);
    Page<ArticleUser> findAllByUserId(Long uid, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "insert into article_user(user_id, article_id) values (?1,?2)",nativeQuery = true)
    void insertArticleIdAndUserId(Long user_id,Long article_id);

    @Transactional
    @Modifying
    @Query(value = "update article_user set delete_time = ?1 where article_id = ?2",nativeQuery = true)
    void deleteArticle(Date date, Long articleId);
}
