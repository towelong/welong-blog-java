package io.github.towelong.blog;


import io.github.towelong.blog.BO.ArticleBO;
import io.github.towelong.blog.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void t(){
        ArticleBO articleBO = new ArticleBO();
        articleBO.setTitle("The beautiful girl");
        articleBO.setDescription("girls skir");
        articleBO.setContent("a girl is very beautiful!");
        articleBO.setImage("http://www.girls.com");
        articleRepository.addArticle(articleBO.getTitle(),articleBO.getDescription(),articleBO.getImage(),articleBO.getContent());
//        articleRepository.addArticle(articleBO);
    }

}
