package com.msm.admin.modules.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2019/3/12 10:05
 */
@Data
@NoArgsConstructor
public class PageResultBean<T> {

    /**
     * 下免四个方法暂时无用
     */
    public static final int SUCCESS = 0;

    public static final int CHECK_FAIL = 1;

    public static final int NO_PERMISSION = 2;

    public static final int UNKNOWN_EXCEPTION = -99;

    /**
     * 返回的信息(主要出错的时候使用)
     */
    private String msg;
    private int code;

    // 当前页
    private Long current;
    // 每页显示的总条数
    private Long size;
    // 总条数
    private Long total;
    //总页数
    private Long pages;

    private T data;




    public static PageResultBean build(IPage page) {
        PageResultBean<Object> resultBean = new PageResultBean<>();
        resultBean.setData(page.getRecords());
        resultBean.setSize(page.getSize());
        resultBean.setTotal(page.getTotal());
        resultBean.setPages(page.getPages());
        resultBean.setCurrent(page.getCurrent());
        return resultBean;
    }

    public static PageResultBean build(Map map) {
        PageResultBean<Object> resultBean = new PageResultBean<>();

        Integer size = (Integer) map.get("size");
        Integer total = (Integer) map.get("total");
        Integer pages = (Integer) map.get("pages");
        Integer current = (Integer) map.get("current");

        resultBean.setData(map.get("records"));
        resultBean.setSize(size.longValue());
        resultBean.setTotal(total.longValue());
        resultBean.setPages(pages.longValue());
        resultBean.setCurrent(current.longValue());
        return resultBean;
    }
}
