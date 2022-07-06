package com.msm.admin.modules.relic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.msm.admin.modules.common.entity.BaseEntity;
import com.msm.admin.modules.common.entity.TreeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author quavario@gmail.com
 * @date 2019/12/19 9:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("msm_relic_catg")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelicCategory extends TreeData<RelicCategory> {

    /**
     * 文物类别名称
     * */
    private String name;

    /** 类型 */
    private String type;

    /** 排序 */
    private Integer sort;


    /** 状态 */
    private Integer status;

    /** 备注 */
    private String remarks;
}
