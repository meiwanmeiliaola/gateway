package com.msm.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.msm.admin.modules.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author quavario@gmail.com
 * @date 2019/12/18 17:25
 */
@Data
public class SysDictType extends BaseEntity {
    /** 字典主键 */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /** 字典名称 */
    private String name;

    /** 字典类型 */
    private String type;

    /** 状态（1正常 0停用） */
    private Integer status;

    /** 描述 */
    private String remarks;

}
