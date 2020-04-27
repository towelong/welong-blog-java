/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/16 13:08
 */
package io.github.towelong.blog.service;


import io.github.towelong.blog.BO.ArticleBO;
import io.github.towelong.blog.model.Article;
import io.github.towelong.blog.model.ArticleUser;
import io.github.towelong.blog.repository.ArticleRepository;
import io.github.towelong.blog.repository.ArticleUserRepository;
import io.github.towelong.blog.repository.TagArticleRepository;
import io.github.towelong.blog.utils.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ArticleService {

    @Autowired
    private ArticleUserRepository articleUserRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagArticleRepository tagArticleRepository;


    /**
     * 查询用户发布的文章
     * @param uid
     * @return <>List</>
     */
    public Page<ArticleUser> findUserArticle(Long uid,Integer pageNum, Integer size){
        Pageable pageable = PageRequest.of(
                pageNum,
                size,
                Sort.by("createTime").descending());
        return articleUserRepository.findAllByUserId(uid,pageable);
    }


    public Page<Article> getLatestPagingArticle(Integer pageNum, Integer size){
        Pageable pageable = PageRequest.of(
                pageNum,
                size,
                Sort.by("createTime").descending());
        return articleRepository.findAllByStatus(true,pageable);
    }

    public Page<Article> getLatestPagingArticleAll(Integer pageNum, Integer size){
        Pageable pageable = PageRequest.of(
                pageNum,
                size,
                Sort.by("createTime").descending());
        return articleRepository.findAll(pageable);
    }


    @Transactional
    public void addArticle(ArticleBO article, Long tid){

        // 1. 添加文章到article
        articleRepository.addArticle(
                article.getTitle(),
                article.getDescription(),
                article.getImage(),
                article.getContent());
        // 2. 添加文章id和用户id到article_user表
        Long aid = articleRepository.getCurrentAid();
        Long uid = RequestHelper.getUid();
        articleUserRepository.insertArticleIdAndUserId(uid, aid);
        // 3. 添加文章id 和 标签id 到 tag_article表
        tagArticleRepository.insertTagIdAndArticleId(tid, aid);
    }

    public void updateStatus(Long id,Long status){
        articleRepository.updateArticleStatus(id, status);
    }

    public void updateArticle(ArticleBO articleBO){

        articleRepository.updateArticle(
                articleBO.getTitle(),
                articleBO.getDescription(),
                articleBO.getImage(),
                articleBO.getContent(),
                articleBO.getId()
                );
        // tag_id没有的话就直接更新文章，若有，就去tag_article中更新tag_id
        tagArticleRepository.updateTagArticle(articleBO.getTagId(),articleBO.getId());

    }

    // TODO: 一篇文章只对应一个标签，待改进

    public void deleteArticle(Long id){
        articleRepository.deleteArticle(new Date(),id);
        articleUserRepository.deleteArticle(new Date(),id);
    }

}
