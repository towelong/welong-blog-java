/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/20 10:09
 */
package io.github.towelong.blog.VO;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PagingDozer<T, K> extends Paging {
    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> pageT, Class<K> classK) {
        this.initPageParameters(pageT);

        List<T> tList = pageT.getContent(); // 从数据库取出来的数据
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> voList = new ArrayList<>(); // 处理取出来的数据类
        tList.forEach(t -> {
            K vo = mapper.map(t, classK); // 做属性拷贝,将T类的属性拷贝到K类
            voList.add(vo);
        });
        this.setItems(voList);
    }
}
