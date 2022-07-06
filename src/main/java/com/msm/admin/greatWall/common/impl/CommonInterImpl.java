package com.msm.admin.greatWall.common.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.msm.admin.greatWall.common.CommonInter;
import com.msm.admin.greatWall.common.SuperCommonInter;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;
import com.msm.admin.greatWall.gwRelic.mapper.GwRelicServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxy
 * @date 2022/3/3 15:25
 */
@Service
public class CommonInterImpl<M extends BaseMapper<T>,T> implements CommonInter<T> {



    @Override
    public boolean existName(T entity, Wrapper<T> queryWrapper) {
        Class<?> cls = entity.getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        String keyProperty = tableInfo.getKeyProperty();
        System.out.println("keyProperty:"+keyProperty);
        Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
        System.out.println("id:"+idVal);
        return false;
    }

}
