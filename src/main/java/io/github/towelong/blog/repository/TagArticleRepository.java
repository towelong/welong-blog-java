/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:03
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.TagArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TagArticleRepository extends JpaRepository<TagArticle, Long> {
    /**
     * 一个tag可以包含多个article
     * @param tagId
     * @return
     */
    List<TagArticle> findAllByTagIdOrderByCreateTimeDesc(Long tagId);

    @Transactional
    @Modifying
    @Query(value = "insert into tag_article(tag_id, article_id) values (?1,?2)",nativeQuery = true)
    void insertTagIdAndArticleId(Long tid,Long aid);

    @Transactional
    @Modifying
    @Query(value = "update tag_article set delete_time = ?1 where id = ?2",nativeQuery = true)
    void deleteTagIdAndArticleId(Date date, Long id);

    @Query("select tg from TagArticle tg where tg.tag.id = ?1")
    List<TagArticle> findAllById(Long id);

    TagArticle findOneByArticleId(Long article_id);

    @Transactional
    @Modifying
    @Query(value = "update tag_article set tag_id = ?1 where article_id=?2",nativeQuery = true)
    void updateTagArticle(Long tag_id,Long article_id);
}
