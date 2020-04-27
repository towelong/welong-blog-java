/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/16 13:07
 */
package io.github.towelong.blog.api.v1;


import io.github.towelong.blog.BO.ArticleBO;
import io.github.towelong.blog.BO.PageCounter;
import io.github.towelong.blog.VO.ArticleSimpleVO;
import io.github.towelong.blog.VO.PagingDozer;
import io.github.towelong.blog.annotation.AdminRequired;
import io.github.towelong.blog.annotation.LoginRequired;
import io.github.towelong.blog.exception.http.Success;
import io.github.towelong.blog.model.Article;
import io.github.towelong.blog.model.ArticleUser;
import io.github.towelong.blog.service.ArticleService;
import io.github.towelong.blog.utils.CommonUtil;
import io.github.towelong.blog.utils.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询当前用户发布的文章
     * @return
     */
    @GetMapping("/user")
    @LoginRequired
    public PagingDozer<ArticleUser,ArticleUser> getUserArticle(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count){
        long uid = RequestHelper.getUid();
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start,count);
        Page<ArticleUser> articlePage = articleService.findUserArticle(
                uid,
                pageCounter.getPage(),
                pageCounter.getCount());
        return new PagingDozer<>(articlePage,ArticleUser.class);
    }

    /**
     * 查询所有的文章，不管status的状态
     * 管理员权限
     * @return
     */
    @GetMapping("/all")
    @AdminRequired
    public PagingDozer<Article, ArticleSimpleVO> getLatestArticleAll(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start,count);
        Page<Article> articlePage = articleService.getLatestPagingArticleAll(
                pageCounter.getPage(),
                pageCounter.getCount());
        return new PagingDozer<>(articlePage,ArticleSimpleVO.class);
    }


    /**
     * 展示给所有用户看的文章列表,status的状态为true
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/latest")
    public PagingDozer<Article, ArticleSimpleVO> getLatestArticle(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start,count);
        Page<Article> articlePage = articleService.getLatestPagingArticle(
                pageCounter.getPage(),
                pageCounter.getCount());
        return new PagingDozer<>(articlePage,ArticleSimpleVO.class);
    }

    @PostMapping("/make")
    @LoginRequired
    public void makeArticle(@RequestBody ArticleBO articleBO){
        articleService.addArticle(articleBO, articleBO.getTagId());
        throw new Success(0);
    }

    @PutMapping("/status")
    @AdminRequired
    public void updateStatus(@RequestBody Map<String,Long> map){
        articleService.updateStatus(map.get("id"),map.get("status"));
        throw new Success(0);
    }

    @PutMapping("/edit")
    @AdminRequired
    public void updateArticle(@RequestBody ArticleBO articleBO){
        articleService.updateArticle(articleBO);
        throw new Success(0);
    }

    @DeleteMapping("/delete")
    @AdminRequired
    public void delete(@PathParam(value = "id")Long id){
        articleService.deleteArticle(id);
        throw new Success(0);
    }

}
