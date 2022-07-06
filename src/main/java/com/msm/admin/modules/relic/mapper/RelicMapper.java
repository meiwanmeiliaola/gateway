package com.msm.admin.modules.relic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msm.admin.greatWall.gwRelic.entity.GwRelic;
import com.msm.admin.modules.relic.entity.Relic;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;
import java.util.Map;

/**
 * @Description: area
 * @Author: quavario
 * @Date:   2019-05-30
 * @Version: V1.0
 */
@CacheNamespace()
public interface RelicMapper extends BaseMapper<Relic> {
    List<Map<String, String>> selectRelicInfo();

    Relic pubSelectById(String id);

    void updateByCateId(GwRelic gwRelic);
}
