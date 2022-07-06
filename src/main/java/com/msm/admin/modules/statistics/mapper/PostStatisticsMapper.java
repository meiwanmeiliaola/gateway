package com.msm.admin.modules.statistics.mapper;

import com.msm.admin.modules.statistics.entity.ChartData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author quavario@gmail.com
 * @date 2020/1/2 15:51
 */
@Mapper
public interface PostStatisticsMapper {

    /** 按部门统计 */
    List<ChartData> selectByDepart();

    /** 按发布用户统计 */
    List<ChartData> selectByUser();

    /** 按类型统计 */
    List<ChartData> selectByType();

    /** 按状态统计 */
    List<ChartData> selectByStatus();


    /** 文章总数 */
    Integer selectTotal();

    /** 最近新增 */
    Integer selectRecentlyAdded();

    /** 今日新增 */
    Integer selectTodayAdded();

    /** 发布文章最多的部门 */
    String selectTopCount();

}
