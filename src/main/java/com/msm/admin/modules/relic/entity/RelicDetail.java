package com.msm.admin.modules.relic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author quavario@gmail.com
 * @date 2019/12/19 9:25
 */
@Data
@TableName("msm_relic_detail")
@Accessors(chain = true)
public class RelicDetail {

    @TableId(type = IdType.ASSIGN_UUID)
    private String relicId;
    /** 数量 */
    private Integer count;

    /** 文物级别 */
    private String levelId;

    /** 文物来源 */
    private String sourceId;

    /** 完残程度 */
    private String integrityId;

    /** 收藏日期 */
    @TableField("coll_date_id")
    private String collectionDateId;

    /** 保存状态ID */
    @TableField("pres_state_id")
    private String preservationStateId ;

    /** 材质ID */
    @TableField("mat_id")
    private String materialId;

    /** 所在地区 */
    private String areaId;

    /** 重量 */
    private float weight;
}
