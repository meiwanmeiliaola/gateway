package com.msm.admin.greatWall.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @author zxy
 * @date 2022/3/3 14:29
 */
public interface CommonInter<T>{



    /**
    * 判断名称是否存在
    */
    boolean existName(T entity, Wrapper<T> queryWrapper);


}
