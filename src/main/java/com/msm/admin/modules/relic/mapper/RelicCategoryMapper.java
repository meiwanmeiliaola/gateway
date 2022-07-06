package com.msm.admin.modules.relic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.modules.relic.entity.Relic;
import com.msm.admin.modules.relic.entity.RelicCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: category
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
public interface RelicCategoryMapper extends BaseMapper<RelicCategory> {
    List<RelicCategory> selectListByParentId(String parentId);


}
