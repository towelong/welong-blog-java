/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 10:55
 */
package io.github.towelong.blog.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class CopyProps {
    /**
     * List 拷贝
     * @param kList
     * @param classT
     * @param <T> VO对象
     * @param <K> 源类
     * @return
     */
    public static <T, K> List<T> copyProperties(List<K> kList,Class<T> classT ){
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<T> tList = new ArrayList<>();
        kList.forEach(k -> {
            T vo = mapper.map(k, classT);
            tList.add(vo);
        });
        return tList;
    }
}
