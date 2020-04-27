/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:08
 */
package io.github.towelong.blog.service;

import io.github.towelong.blog.VO.TagArticleVO;
import io.github.towelong.blog.model.Tag;
import io.github.towelong.blog.model.TagArticle;
import io.github.towelong.blog.repository.TagArticleRepository;
import io.github.towelong.blog.repository.TagRepository;
import io.github.towelong.blog.utils.CopyProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagArticleRepository tagArticleRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<TagArticleVO> findByTagId(Long tagId){
        List<TagArticle> tagArticleList = tagArticleRepository.findAllByTagIdOrderByCreateTimeDesc(tagId);
        return CopyProps.copyProperties(tagArticleList,TagArticleVO.class);
    }

    public List<Tag> findAll(){
        return tagRepository.findAllByOrderByCreateTimeDesc();
    }

    public void addTag(Tag tag){
        tagRepository.insertTag(tag.getTagName());
    }

    public void updateTag(Tag tag){
        tagRepository.updateTag(tag.getTagName(),tag.getId());
    }

    /**
     * 删除标签会删除改标签下的捆绑的文章
     * @param tag_id
     */
    public void deleteTag(Long tag_id){
//        查询出tag_article中 tag_id 为 7的列
        List<TagArticle> tagArticleList = tagArticleRepository.findAllById(tag_id);
        // 符合条件的删除
        for(TagArticle tagArticle:tagArticleList){
            Long id = tagArticle.getId();
            tagArticleRepository.deleteTagIdAndArticleId(new Date(),id);
        }
        tagRepository.deleteTag(new Date(),tag_id);
    }

    public TagArticle getTagArticle(Long article_id){
        return tagArticleRepository.findOneByArticleId(article_id);
    }
}
