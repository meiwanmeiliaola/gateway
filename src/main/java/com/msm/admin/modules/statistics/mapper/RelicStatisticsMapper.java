package com.msm.admin.modules.statistics.mapper;

import com.msm.admin.modules.statistics.entity.ChartData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2020/1/2 15:51
 */
@Mapper
public interface RelicStatisticsMapper {
    List<ChartData> selectByArea();

    List<ChartData> selectByDepart();

    /**
     * @return 文物数量月增长值
     */
    Integer selectMonthRise();

    /**
     * @return 文物总数
     */
    Integer selectTotal();

    /**
     * @return 发布文物数量最多的博物馆
     */
    Map selectTopCountDep();


    List<Map> selectCountByPresState();

    List<Map> selectCountByIntegrity();
    List<Map> selectCountBySource();

    List<ChartData> selectInfoStat();

    List<ChartData> selectInfoStat2();

    String getByMaterialId(@Param("materialId") String materialId, @Param("type") String type);
}
