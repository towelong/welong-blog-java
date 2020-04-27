/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/16 11:53
 */
package io.github.towelong.blog.repository;

import io.github.towelong.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Article findOneById(Long id);


    Page<Article> findAllByStatus(boolean status,Pageable pageable);



    @Transactional
    @Modifying
    @Query(value = "insert into article(title, description, image, content) values (?1,?2,?3,?4)",nativeQuery = true)
    void addArticle(String title,String description,String image,String content);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    Long getCurrentAid();

    @Transactional
    @Modifying
    @Query(value = "update article set title=?1,description=?2,image=?3,content=?4 where id=?5",nativeQuery = true)
    void updateArticle(String title,String description,String image,String content,Long id);

    @Transactional
    @Modifying
    @Query(value = "update article set status = ?2 where id=?1",nativeQuery = true)
    void updateArticleStatus(Long id,Long status);

    @Transactional
    @Modifying
    @Query(value = "update article set delete_time = ?1 where id = ?2",nativeQuery = true)
    void deleteArticle(Date date, Long articleId);
}
