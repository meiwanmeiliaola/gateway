package com.msm.admin.modules.statistics.mapper;

import com.msm.admin.modules.statistics.entity.ChartData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2020/1/2 15:51
 */
@Mapper
public interface ExhibitionStatisticsMapper {

    /** 按部门统计 */
    List<ChartData> selectByDepart();


    /** 按类型统计 */
    List<ChartData> selectByType();



    /** 发布文章最多的部门 */
    String selectTopCount();

    /** 最近新增 */
    Integer selectRecentlyAdded();

    /** 根据类型查询总数 */
    Integer selectTotalByType(Integer type);


}
