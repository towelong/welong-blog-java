/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/20 10:08
 */
package io.github.towelong.blog.VO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {
    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<T> items;

    public Paging(Page<T> pageT) {
        this.initPageParameters(pageT);
        this.items = pageT.getContent();
    }

    void initPageParameters(Page<T> pageT) {
        this.total = pageT.getTotalElements();
        this.count = pageT.getSize();
        this.page = pageT.getNumber();
        this.totalPage = pageT.getTotalPages();
    }
}
