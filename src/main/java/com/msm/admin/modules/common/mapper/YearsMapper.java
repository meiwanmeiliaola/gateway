package com.msm.admin.modules.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.common.entity.Years;

import java.util.List;

/**
 * @Description: 年代
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface YearsMapper extends BaseMapper<Years> {
    List<Years> selectListByParentId(String parentId);
}
