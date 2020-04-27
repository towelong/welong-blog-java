/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 13:10
 */
package io.github.towelong.blog.api.v1;

import io.github.towelong.blog.VO.TagArticleVO;
import io.github.towelong.blog.annotation.AdminRequired;
import io.github.towelong.blog.exception.http.Success;
import io.github.towelong.blog.model.Tag;
import io.github.towelong.blog.model.TagArticle;
import io.github.towelong.blog.service.TagService;
import io.github.towelong.blog.utils.CopyProps;
import io.github.towelong.blog.VO.TagAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/tag")
@Validated
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 查询tag分类下的文章
     * @param id
     * @return
     */
    @GetMapping("")
    public List<TagArticleVO> getTagArticle(
            @PathParam("id")
            @Positive(message = "id是正整数") Long id){
        List<TagArticleVO> tagArticleVOList = tagService.findByTagId(id);
        return tagArticleVOList.stream()
                .filter(tagArticleVO -> tagArticleVO.getTag().getDeleteTime()==null)
                .collect(Collectors.toList());
    }

    /**
     * 用户访问的接口
     * 查询所有的tag标签，过滤掉被删除的标签
     * @return
     */
    @GetMapping("/all")
    public List<Tag> getAll(){
        List<Tag> tagList = tagService.findAll();
        // 过滤出 delete_time==null 的列
        return tagList.stream().filter(tag -> tag.getDeleteTime()==null).collect(Collectors.toList());
    }

    /**
     * 查询所有的tag标签
     * @return
     */
    @GetMapping("/admin/all")
    @AdminRequired
    public List<TagAdminVO> getAdminAll(){
        List<Tag> tagList = tagService.findAll();
        return CopyProps.copyProperties(tagList, TagAdminVO.class);
    }

    /**
     * 添加标签
     * @param tag
     */
    @PostMapping("/add")
    @AdminRequired
    public void addTag(@RequestBody Tag tag){
        tagService.addTag(tag);
        throw new Success(0);
    }


    @PutMapping("/edit")
    @AdminRequired
    public void updateTag(@RequestBody Tag tag){
        tagService.updateTag(tag);
        throw new Success(0);
    }


    @DeleteMapping("/delete")
    @AdminRequired
    public void deleteTag(@RequestParam(value = "id") Long tagId ){
        tagService.deleteTag(tagId);
        throw new Success(0);
    }

    @GetMapping("/{articleId}")
    public TagArticle getTagIdByArtId(@PathVariable Long articleId){
        return tagService.getTagArticle(articleId);
    }
}
