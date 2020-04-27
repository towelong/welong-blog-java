/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/20 12:19
 */
package io.github.towelong.blog.utils;

import io.github.towelong.blog.BO.PageCounter;

public class CommonUtil {

    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum; // 页码
        pageNum = start / count; // start表示第几条数据，count表示有几条数据
        return PageCounter
                .builder()
                .count(count)
                .page(pageNum)
                .build();
    }
}
